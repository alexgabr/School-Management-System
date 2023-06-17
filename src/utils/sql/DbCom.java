//
//Copyright Alexandru Vrincianu
//2023
//

package utils.sql;

public class DbCom {
    // select
    public static String select(String columns, String table) {
        return "SELECT " + columns + " FROM " + table;
    }

    public static String select(String columns, String table, String where) {
        return select(columns, table) + " WHERE " + where;
    }

    // insert
    public static String insert(String table, String values) {
        return "INSERT INTO " + table + " VALUES " + values;
    }

    public static String insertSpecColumns(String table, String columns, String values) {
        return "INSERT INTO " + table + "(" + columns + ")" + " VALUES " + values;
    }

    public static String insertSpecColumns(String table, String columns, String values, String where){
        return insertSpecColumns(table, columns, values) + " WHERE " + where;
    }

    // alter table
    public static String rename(String table, String columnName, String newName) {
        return "ALTER TABLE " + table + " RENAME " + columnName + " TO " + newName;
    }

    // update
    public static String updateRows(String table, String column, String rowValue, String valueDataType) {
        String ret = "";

        if (valueDataType.equals("int") || valueDataType.equals("INT")) {
            ret = "UPDATE " + table + " SET " + column + " = " + Integer.parseInt(rowValue);
        } else if (valueDataType.equals("decimal") || valueDataType.equals("DECIMAL")) {
            ret = "UPDATE " + table + " SET " + column + " = " + Double.parseDouble(rowValue);
        } else if (valueDataType.equals("date") || valueDataType.equals("DATE") || valueDataType.equals("varchar")
                || valueDataType.equals("VARCHAR")) {
            ret = "UPDATE " + table + " SET " + column + " = " + rowValue;
        } else {
            System.out.println("ERROR");
        }

        return ret;
    }

    public static String updateRows(String table, String column, String rowValue, String valueDataType, String where) {
        return updateRows(table, column, rowValue, valueDataType) + " WHERE " + where;
    }

    // delete
    public static String deleteAllRows(String table) {
        return "DELETE FROM " + table;
    }

    public static String deleteRow(String table, String where) {
        return deleteAllRows(table) + " WHERE " + where;
    }
}