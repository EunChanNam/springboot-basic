package prgms.spring_week1.domain.util.sqlBuilder.builder.tableBuilder;

import static prgms.spring_week1.domain.util.sqlBuilder.LastIndexLength.INVALID_LAST_COMMA;

public class Column {
    private StringBuilder column;

    public Column(StringBuilder column) {
        this.column = column;
    }

    public static ColumnBuilder builder(){
        return new ColumnBuilder();
    }

    public static class ColumnBuilder {
        private StringBuilder columnBuilder = new StringBuilder();

        public ColumnBuilder columns(String... columns) {
            for (String column : columns) {
                columnBuilder.append(column + ",");
            }

            columnBuilder.setLength(columnBuilder.length() - INVALID_LAST_COMMA);

            return this;
        }

        public Column build() {
            return new Column(columnBuilder);
        }
    }
    public String getQuery() {
        return column.toString();
    }
}
