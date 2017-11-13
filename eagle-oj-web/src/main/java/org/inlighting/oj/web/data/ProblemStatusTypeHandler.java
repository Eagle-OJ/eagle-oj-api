package org.inlighting.oj.web.data;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.inlighting.oj.judge.config.ProblemStatusEnum;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Smith
 **/
public class ProblemStatusTypeHandler extends BaseTypeHandler<ProblemStatusEnum> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, ProblemStatusEnum parameter, JdbcType jdbcType) throws SQLException {
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
}
