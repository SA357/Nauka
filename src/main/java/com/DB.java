package com;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.nio.file.Paths;
import java.sql.Date;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;



public class DB {

    final String[] mark_types = {"Я", "Н", "В", "Рв", "Б", "К", "ОТ", "До", "Хд", "У", "Ож"};

    private Connection getConnection() throws SQLException {
        String connStr = "jdbc:sqlite:DB.db";  //"jdbc:sqlite::memory:"
        return DriverManager.getConnection(connStr);
    }

    public void create() throws SQLException {
       // try (Connection conn = getConnection()) {
            executeLinesFromFile("src/main/resources/sql/sql.txt");
            executeLinesFromFile("src/main/resources/sql/example.txt");
            createCalendar();
            createMarkTypes();
            addRandomMarks();
            //Statement stmt = conn.createStatement();

            //stmt.executeUpdate("create table Zakazi ( name text, date timestamp, weapon text, scope text, podstvolnik text, id SERIAL )");
//            addUser("ADMIN", "111", Date.valueOf(LocalDate.now()), true);
//            addUser("Бумбершнюк", "111", Date.valueOf(LocalDate.now()), false);
//            addUser("Бусыгин Константин Николаевич", "111", Date.valueOf(LocalDate.now()), true);
       //}
    }

    private void createCalendar() throws SQLException {
        try (Connection conn = getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("INSERT into Calendar(date, date_type) values (?, ?)");
            Date tempDate = new Date(120, 0, 1);
            Date end = Date.valueOf(LocalDate.of(2021,1,1));
            while (tempDate.getTime() < end.getTime()) {
                stmt.setDate(1, tempDate);
                stmt.setString(2, "Р");
                stmt.executeUpdate();
                tempDate = Date.valueOf(tempDate.toLocalDate().plusDays(1));
            }
//            for (int month = 1; month <= 12; month++) {
//                for (int day = 1; day <= 28; day++) {
//                    stmt.setDate(1, new Date(LocalDate.now().getYear(), month, day));
//                    stmt.setString(2, "Р");
//                    stmt.executeUpdate();
//                }
//            }
        }
    }

    private void createMarkTypes() throws SQLException {
        try (Connection conn = getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("INSERT into Mark(mark_type) values (?)");
            for (String mark : mark_types){
                stmt.setString(1, mark);
                stmt.executeUpdate();
            }
        }
    }

    private void addRandomMarks() throws SQLException {
        System.out.println("> addRandomMarks");
        Set<Integer> ids = getEmployeesIds();
        Random random = new Random();
        try (Connection conn = getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("INSERT into Emp_mark(date, mark_type, emp_id) values (?, ?, ?)");
            for(int id : ids){
                Date tempDate = new Date(LocalDate.now().getYear()-1900, 0, 1);
                Date end = Date.valueOf(LocalDate.now());
                while (tempDate.getTime() <= end.getTime()) {
                    stmt.setDate(1, tempDate);
                    stmt.setString(2, mark_types[random.nextInt(mark_types.length)]);
                    stmt.setInt(3, id);
                    stmt.executeUpdate();
                    tempDate = Date.valueOf(tempDate.toLocalDate().plusDays(1));
                }
//                for (int month = 1; month <= LocalDate.now().getMonth().getValue(); month++) {
//                    for (int day = 1; day <= 28; day++) {
//                        stmt.setDate(1, new Date(LocalDate.now().getYear(), month, day));
//                        stmt.setString(2, mark_types[random.nextInt(mark_types.length)]);
//                        stmt.setInt(3, id);
//                        stmt.executeUpdate();
//                    }
//                }
            }
        }
    }

    private Set<Integer> getEmployeesIds() throws SQLException {
        Set<Integer> ids = new HashSet<>();
        try (Connection conn = getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT emp_id from Employee");
            while (rs.next()){
                ids.add(rs.getInt(1));
            }
            return ids;
        }
    }

    /**
     * @return  Month - Set of day_types
     */
    public Map<Integer, List<String>> getCalendar() throws SQLException {
        Map<Integer, List<String>> map = new HashMap<>();
        try (Connection conn = getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select date, date_type from Calendar");
            int month;
            while (rs.next()) {
                Date date = rs.getDate(1);
                month = date.toLocalDate().getMonth().getValue();
                if (!map.containsKey(month)) {
                    map.put(month, new ArrayList<>());
                }
                String date_type = rs.getString(2);
                map.computeIfPresent(month, (k, v) -> {
                    v.add(date_type);  //не упорядочено - переделоть
                    return v;
                });
            }
        }
        return map;
    }

    public Set<Integer> getEmployeesId(String department) throws SQLException {
        Set<Integer> id = new HashSet<>();
        try (Connection conn = getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("select emp_id from Employee where dep_name = ?");
            stmt.setString(1, department);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                id.add(rs.getInt(1));
            }
        }
        return id;
    }

    public String getMark(int emp_id, Date date) throws SQLException {
        try (Connection conn = getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("select mark_type from Emp_mark where emp_id = ? and date = ?");
            stmt.setInt(1, emp_id);
            stmt.setDate(2, date);
            ResultSet rs = stmt.executeQuery();
            return rs.getString(1);
        }
    }

    public Set<String> getDepartments() throws SQLException{
        Set<String> departments = new HashSet<>();
        try (Connection conn = getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select dep_name from Department");
            while (rs.next()) {
                departments.add(rs.getString(1));
            }
        }
        return departments;
    }

    public String getEmployeesFullName(int id) throws SQLException{
        try (Connection conn = getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("select emp_surname, emp_name, emp_patronymic from Employee where emp_id = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            return  String.join(" ",  rs.getString(1), rs.getString(2), rs.getString(3));
        }
    }

    public String  getEmployeesPosition(int id) throws SQLException{
        try (Connection conn = getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("select emp_position from Employee where emp_id = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            return rs.getString(1);
        }
    }



    public void addUser(String name, String password, Date date, boolean isAdmin) throws SQLException {
        try (Connection conn = getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("insert into User values(?, ?, ?, ?)");
            stmt.setString(1, name);
            stmt.setString(2, password);
            stmt.setDate(3, date);
            stmt.setBoolean(4, isAdmin);
            stmt.execute();
        }
    }

    public void addActiveClient(String name, String IP, int clientServerPartPort, Date date) throws SQLException {
        try (Connection conn = getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("insert into activeClients values(?, ?, ?, ?)");
            stmt.setString(1, name);
            stmt.setString(2, IP);
            stmt.setInt(3, clientServerPartPort);
            stmt.setDate(4, date);
            stmt.execute();
        }
    }

    public void addActiveSession(String player1, String player2, Date date) throws SQLException {
        try (Connection conn = getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("insert into ActiveSessions(player1, player2, date) values (?, ?, ?)");
            stmt.setString(1, player1);
            stmt.setString(2, player2);
            stmt.setDate(3, date);
            stmt.execute();
        }
    }

    public void closeActiveSession(String player, String player2) throws SQLException{

    }

    void closeAllActiveSession() throws SQLException {
        try (Connection conn = getConnection()) {
            conn.createStatement().execute("DELETE FROM activeSessions");
        }
    }

    public void addLog(String name, Date date, String text) throws SQLException {
        try (Connection conn = getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("insert into LOG values(?, ?, ?)");
            stmt.setString(1, name);
            stmt.setDate(2, date);
            stmt.setString(3, text);
            stmt.execute();
        }
    }

//    public AdminQueryReplyMessage executeAdminQuery(AdminQueryMessage msg) throws SQLException { //работает только для одной строки LOG
//        try (Connection conn = getConnection()) {
////            String needwords = "text like '%" + String.join("%' and text like %'", msg.getWords().split(",")) + "%'";
////            System.out.println(needwords);
//            String query = "select name, date, text from LOG where 1";
//            if (!msg.getNameOfUser().equals("")) {
//                query += " and name = ?";
//            }
//            if (!msg.getWords().equals("")) {
//                query += " and text like ?";
//            }
//            if (msg.getDate() != null) {
//                query += " and date >= ?";
//            }
//            System.out.println(query);
//            PreparedStatement stmt = conn.prepareStatement(query);
//            int cnt = 1;
//            if (!msg.getNameOfUser().equals("")) {
//                stmt.setString(cnt++, msg.getNameOfUser());
//            }
//            if (!msg.getWords().equals("")) {
//                stmt.setString(cnt++, "%" + msg.getWords() + "%");
//            }
//            if (msg.getDate() != null) {
//                stmt.setDate(cnt++, msg.getDate());
//            }
//            ResultSet rs = stmt.executeQuery();
//            AdminQueryReplyMessage replyMessage = new AdminQueryReplyMessage();
//            while (rs.next())
//                replyMessage.getList().add(
//                        new AdminQueryReplyMessage.Entry(
//                                rs.getString(1), rs.getString(3), rs.getDate(2).toLocalDate().toString()
//                        )
//                );
//            return replyMessage;
////            if(!msg.getNameOfUser().equals("")) {
////                PreparedStatement stmt = conn.prepareStatement("select name, date, text from LOG where name = ? and date >= ? and "+needwords);
//////                PreparedStatement stmt = conn.prepareStatement("select name, date, text from LOG where " + needwords);
////                stmt.setString(1, msg.getNameOfUser());
////                stmt.setDate(2, msg.getDate());
////                stmt.execute();
////                ResultSet rs = stmt.getResultSet();
////                return new QueryReaplyMessage(rs.getString(1), rs.getString(3), rs.getDate(2));
////            }
////            else {
////                PreparedStatement stmt = conn.prepareStatement("select name, date, text from LOG where date >= ? and "+needwords);
////                stmt.setDate(1, msg.getDate());
////                stmt.execute();
//        }
//    }

    boolean isAdmin(String name, String password) throws SQLException {
        try (Connection conn = getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT EXISTS(SELECT password FROM Clients WHERE password = '"
                    + password + "' and name = '" + name + "' and isAdmin =" + true + ")");
            return rs.getBoolean(1);
        }
    }

    boolean isVerified(String name, String password) throws SQLException {
        try (Connection conn = getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT EXISTS(SELECT password FROM Clients WHERE password = '" + password + "' and name = '" + name + "')");
            return rs.getBoolean(1);
        }
    }

    public String getPassword(String name) throws SQLException {
        try (Connection conn = getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT password FROM Clients WHERE name = '" + name+"'");
            return rs.getString(1);   //
        }
    }

    String getName(InetSocketAddress inetAddress) throws SQLException {
        try (Connection conn = getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT name FROM activeClients WHERE IP = '" + inetAddress.getAddress().toString().substring(1)
                    + "' and portServerPart = "+ inetAddress.getPort());
            return rs.getString(1);   //1 or
        }
    }

    InetSocketAddress getInetSocketAddress(String name) throws SQLException {
        try (Connection conn = getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT IP, portServerPart FROM activeClients WHERE name = '" + name + "'");
            return new InetSocketAddress(rs.getString(1), rs.getInt(2));   //
        }
    }

    boolean checkClientNameExistence(String name) throws SQLException {
        try (Connection conn = getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT EXISTS(SELECT name FROM Clients WHERE name = '" + name + "')");
            return rs.getBoolean(1);
        }
    }

    boolean checkClientActiveness(String name) throws SQLException {
        try (Connection conn = getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT EXISTS(SELECT name FROM ActiveClients WHERE name = '" + name + "')");
            return rs.getBoolean(1);
        }
    }

    List<InetSocketAddress> getAllActiveClientsServerParts() throws SQLException {
        List<InetSocketAddress> list = new ArrayList<>();
        try (Connection conn = getConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT IP, portServerPart from activeClients");
            while (rs.next()) {
                list.add(new InetSocketAddress(rs.getString(1), rs.getInt(2)));
            }
        }
        return list;
    }

    void deleteAllActiveClients() throws SQLException {
        for (InetSocketAddress a : getAllActiveClientsServerParts()) {
            deleteActiveClient(a);
        }
    }

    void deleteActiveClient(InetSocketAddress inetAddress) throws SQLException {
        try (Connection conn = getConnection()) {
            conn.createStatement().execute("DELETE FROM activeClients WHERE IP = '" + inetAddress.getAddress().toString().substring(1)
                    + "' and portServerPart =" + inetAddress.getPort());
        }
    }

    void deleteActiveClient(String name) throws SQLException {
        try (Connection conn = getConnection()) {
            conn.createStatement().execute("DELETE FROM activeClients WHERE name = '" + name + "'");
        }
    }

    void executeLinesFromFile (String file) throws SQLException { // сканнер накапливает строку до ( ; )    и пропускать коменты ( - )
        try (Connection conn = getConnection()) {
            Scanner scan = new Scanner(Paths.get(file));
            StringBuilder stringBuilder = new StringBuilder();
            while (scan.hasNextLine()) {
                String line;
                do {
                    line = scan.nextLine();
                    if (!line.contains("--")) stringBuilder.append(line);
                }
                while (!line.contains(";"));
                line = stringBuilder.toString();
                stringBuilder = new StringBuilder();
                System.out.println(">" + line);
                if (line.endsWith(";")) {
                    line = line.substring(0, line.length() - 1);
                }

                Statement stmt = conn.createStatement();
                if (stmt.execute(line)) {
                    ResultSet rs = stmt.getResultSet();
                    try {
                        showResultSet(rs, System.out);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                stmt.closeOnCompletion();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showResultSet(ResultSet rs, OutputStream os) throws SQLException {
        PrintWriter pw = new PrintWriter(os, true);
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();
        for (int i = 1; i <= columnCount; i++) {                               /// в резалтСетах нумерация начинаеца с 1
            if (i > 1) {
                pw.print(", ");
            }
            pw.print(metaData.getColumnName(i));
        }
        pw.println();
        while (rs.next()) {
            for (int i = 1; i <= columnCount; i++) {
                if (i > 1) {
                    pw.print(", ");
                }
                pw.print(rs.getString(i));
            }
            pw.println();
        }
        pw.println();
    }

    void writeAllTables() throws Exception {
        try (Connection conn = getConnection()) {
            Statement stmt = conn.createStatement();
            for (String table : tableNames()) {
                ResultSet rs = stmt.executeQuery("select * from " + table);
                try (FileOutputStream fo = new FileOutputStream("All_Tables.txt", true)) {
                    PrintWriter pw = new PrintWriter(fo, true);//
                    pw.println("таблица: " + table);
                    showResultSet(rs, fo);
                }
            }
        }
    }

    private Set<String> tableNames() throws Exception {
        Set<String> tableNames = new HashSet<>();
        try (Connection conn = getConnection()) {
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet rs = metaData.getTables(null, null, "", null);
            while (rs.next()) {
                tableNames.add(rs.getString("TABLE_NAME"));//(3) cтолбец (из документациии)
            }
        }
        return tableNames;
    }
}
