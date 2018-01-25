package com.eagleoj.web.data;

/**
 * @author Smith
 **/
/*public class ResultTypeHandler extends BaseTypeHandler<ResultEnum> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, ResultEnum parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.name());
    }

    @Override
    public ProblemStatusEnum getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return ProblemStatusEnum.valueOf(rs.getString(columnName));
    }

    @Override
    public ProblemStatusEnum getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return ProblemStatusEnum.valueOf(rs.getString(columnIndex));
    }

    @Override
    public ProblemStatusEnum getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return ProblemStatusEnum.valueOf(cs.getString(columnIndex));
    }
}*/
