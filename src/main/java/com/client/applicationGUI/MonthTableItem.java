package com.client.applicationGUI;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;

public class MonthTableItem {
    @FXML
    private SimpleStringProperty emp_name;
    @FXML
    private SimpleStringProperty emp_position;
    @FXML
    private SimpleStringProperty emp_id;
    @FXML
    private SimpleStringProperty summary;

    @FXML
    private SimpleStringProperty day1;
    @FXML
    private SimpleStringProperty day2;
    @FXML
    private SimpleStringProperty day3;
    @FXML
    private SimpleStringProperty day4;
    @FXML
    private SimpleStringProperty day5;
    @FXML
    private SimpleStringProperty day6;
    @FXML
    private SimpleStringProperty day7;
    @FXML
    private SimpleStringProperty day8;
    @FXML
    private SimpleStringProperty day9;
    @FXML
    private SimpleStringProperty day10;
    @FXML
    private SimpleStringProperty day11;
    @FXML
    private SimpleStringProperty day12;
    @FXML
    private SimpleStringProperty day13;
    @FXML
    private SimpleStringProperty day14;
    @FXML
    private SimpleStringProperty day15;
    @FXML
    private SimpleStringProperty day16;
    @FXML
    private SimpleStringProperty day17;
    @FXML
    private SimpleStringProperty day18;
    @FXML
    private SimpleStringProperty day19;
    @FXML
    private SimpleStringProperty day20;
    @FXML
    private SimpleStringProperty day21;
    @FXML
    private SimpleStringProperty day22;
    @FXML
    private SimpleStringProperty day23;
    @FXML
    private SimpleStringProperty day24;
    @FXML
    private SimpleStringProperty day25;
    @FXML
    private SimpleStringProperty day26;
    @FXML
    private SimpleStringProperty day27;
    @FXML
    private SimpleStringProperty day28;
    @FXML
    private SimpleStringProperty day29;
    @FXML
    private SimpleStringProperty day30;
    @FXML
    private SimpleStringProperty day31;

    //String summary,
    public MonthTableItem(String name, String position, String emp_id, String day1, String day2, String day3, String day4, String day5,
                          String day6, String day7, String day8, String day9, String day10, String day11, String day12, String day13,
                          String day14, String day15, String day16, String day17, String day18, String day19, String day20, String day21,
                          String day22, String day23, String day24, String day25, String day26, String day27, String day28) {
        this.emp_name = new SimpleStringProperty(name);
        this.emp_position = new SimpleStringProperty(position);
        this.emp_id = new SimpleStringProperty(emp_id);
        //this.summary = new SimpleStringProperty(summary);
        this.day1 = new SimpleStringProperty(day1);
        this.day2 = new SimpleStringProperty(day2);
        this.day3 = new SimpleStringProperty(day3);
        this.day4 = new SimpleStringProperty(day4);
        this.day5 = new SimpleStringProperty(day5);
        this.day6 = new SimpleStringProperty(day6);
        this.day7 = new SimpleStringProperty(day7);
        this.day8 = new SimpleStringProperty(day8);
        this.day9 = new SimpleStringProperty(day9);
        this.day10 = new SimpleStringProperty(day10);
        this.day11 = new SimpleStringProperty(day11);
        this.day12 = new SimpleStringProperty(day12);
        this.day13 = new SimpleStringProperty(day13);
        this.day14 = new SimpleStringProperty(day14);
        this.day15 = new SimpleStringProperty(day15);
        this.day16 = new SimpleStringProperty(day16);
        this.day17 = new SimpleStringProperty(day17);
        this.day18 = new SimpleStringProperty(day18);
        this.day19 = new SimpleStringProperty(day19);
        this.day20 = new SimpleStringProperty(day20);
        this.day21 = new SimpleStringProperty(day21);
        this.day22 = new SimpleStringProperty(day22);
        this.day23 = new SimpleStringProperty(day23);
        this.day24 = new SimpleStringProperty(day24);
        this.day25 = new SimpleStringProperty(day25);
        this.day26 = new SimpleStringProperty(day26);
        this.day27 = new SimpleStringProperty(day27);
        this.day28 = new SimpleStringProperty(day28);
    }
    //String summary,
    public MonthTableItem(String name, String position, String emp_id, String day1, String day2, String day3, String day4, String day5,
                          String day6, String day7, String day8, String day9, String day10, String day11, String day12, String day13,
                          String day14, String day15, String day16, String day17, String day18, String day19, String day20, String day21,
                          String day22, String day23, String day24, String day25, String day26, String day27, String day28, String day29) {
        this(name, position, emp_id, day1, day2, day3, day4, day5, day6, day7, day8, day9, day10, day11, day12, day13, day14, day15,
                day16, day17, day18, day19, day20, day21, day22, day23, day24, day25, day26, day27, day28);
        this.day29 = new SimpleStringProperty(day29);
    }
    //String summary,
    public MonthTableItem(String name, String position, String emp_id, String day1, String day2, String day3, String day4, String day5,
                          String day6, String day7, String day8, String day9, String day10, String day11, String day12, String day13,
                          String day14, String day15, String day16, String day17, String day18, String day19, String day20, String day21,
                          String day22, String day23, String day24, String day25, String day26, String day27, String day28, String day29,
                          String day30) {
        this(name, position, emp_id, day1, day2, day3, day4, day5, day6, day7, day8, day9, day10, day11, day12, day13, day14, day15,
                day16, day17, day18, day19, day20, day21, day22, day23, day24, day25, day26, day27, day28);
        this.day29 = new SimpleStringProperty(day29);
        this.day30 = new SimpleStringProperty(day30);
    }
    //String summary,
    public MonthTableItem(String name, String position, String emp_id, String day1, String day2, String day3, String day4, String day5,
                          String day6, String day7, String day8, String day9, String day10, String day11, String day12, String day13,
                          String day14, String day15, String day16, String day17, String day18, String day19, String day20, String day21,
                          String day22, String day23, String day24, String day25, String day26, String day27, String day28, String day29,
                          String day30, String day31) {
        this(name, position, emp_id, day1, day2, day3, day4, day5, day6, day7, day8, day9, day10, day11, day12, day13, day14, day15,
                day16, day17, day18, day19, day20, day21, day22, day23, day24, day25, day26, day27, day28);
        this.day29 = new SimpleStringProperty(day29);
        this.day30 = new SimpleStringProperty(day30);
        this.day31 = new SimpleStringProperty(day31);
    }

    public String getDay29() {
        return day29.get();
    }

    public SimpleStringProperty day29Property() {
        return day29;
    }

    public String getDay30() {
        return day30.get();
    }

    public SimpleStringProperty day30Property() {
        return day30;
    }

    public String getDay31() {
        return day31.get();
    }

    public SimpleStringProperty day31Property() {
        return day31;
    }

    public String getEmp_name() {
        return emp_name.get();
    }

    public SimpleStringProperty emp_nameProperty() {
        return emp_name;
    }

    public String getEmp_position() {
        return emp_position.get();
    }

    public SimpleStringProperty emp_positionProperty() {
        return emp_position;
    }

    public String getEmp_id() {
        return emp_id.get();
    }

    public SimpleStringProperty emp_idProperty() {
        return emp_id;
    }

    public String getSummary() {
        return summary.get();
    }

    public SimpleStringProperty summaryProperty() {
        return summary;
    }

    public String getDay1() {
        return day1.get();
    }

    public SimpleStringProperty day1Property() {
        return day1;
    }

    public String getDay2() {
        return day2.get();
    }

    public SimpleStringProperty day2Property() {
        return day2;
    }

    public String getDay3() {
        return day3.get();
    }

    public SimpleStringProperty day3Property() {
        return day3;
    }

    public String getDay4() {
        return day4.get();
    }

    public SimpleStringProperty day4Property() {
        return day4;
    }

    public String getDay5() {
        return day5.get();
    }

    public SimpleStringProperty day5Property() {
        return day5;
    }

    public String getDay6() {
        return day6.get();
    }

    public SimpleStringProperty day6Property() {
        return day6;
    }

    public String getDay7() {
        return day7.get();
    }

    public SimpleStringProperty day7Property() {
        return day7;
    }

    public String getDay8() {
        return day8.get();
    }

    public SimpleStringProperty day8Property() {
        return day8;
    }

    public String getDay9() {
        return day9.get();
    }

    public SimpleStringProperty day9Property() {
        return day9;
    }

    public String getDay10() {
        return day10.get();
    }

    public SimpleStringProperty day10Property() {
        return day10;
    }

    public String getDay11() {
        return day11.get();
    }

    public SimpleStringProperty day11Property() {
        return day11;
    }

    public String getDay12() {
        return day12.get();
    }

    public SimpleStringProperty day12Property() {
        return day12;
    }

    public String getDay13() {
        return day13.get();
    }

    public SimpleStringProperty day13Property() {
        return day13;
    }

    public String getDay14() {
        return day14.get();
    }

    public SimpleStringProperty day14Property() {
        return day14;
    }

    public String getDay15() {
        return day15.get();
    }

    public SimpleStringProperty day15Property() {
        return day15;
    }

    public String getDay16() {
        return day16.get();
    }

    public SimpleStringProperty day16Property() {
        return day16;
    }

    public String getDay17() {
        return day17.get();
    }

    public SimpleStringProperty day17Property() {
        return day17;
    }

    public String getDay18() {
        return day18.get();
    }

    public SimpleStringProperty day18Property() {
        return day18;
    }

    public String getDay19() {
        return day19.get();
    }

    public SimpleStringProperty day19Property() {
        return day19;
    }

    public String getDay20() {
        return day20.get();
    }

    public SimpleStringProperty day20Property() {
        return day20;
    }

    public String getDay21() {
        return day21.get();
    }

    public SimpleStringProperty day21Property() {
        return day21;
    }

    public String getDay22() {
        return day22.get();
    }

    public SimpleStringProperty day22Property() {
        return day22;
    }

    public String getDay23() {
        return day23.get();
    }

    public SimpleStringProperty day23Property() {
        return day23;
    }

    public String getDay24() {
        return day24.get();
    }

    public SimpleStringProperty day24Property() {
        return day24;
    }

    public String getDay25() {
        return day25.get();
    }

    public SimpleStringProperty day25Property() {
        return day25;
    }

    public String getDay26() {
        return day26.get();
    }

    public SimpleStringProperty day26Property() {
        return day26;
    }

    public String getDay27() {
        return day27.get();
    }

    public SimpleStringProperty day27Property() {
        return day27;
    }

    public String getDay28() {
        return day28.get();
    }

    public SimpleStringProperty day28Property() {
        return day28;
    }
}
