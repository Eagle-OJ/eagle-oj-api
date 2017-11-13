package org.inlighting.oj.web.data;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.inlighting.oj.judge.config.LanguageEnum;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Smith
 **/
public class CodeLanguageTypeHandler extends BaseTypeHandler<LanguageEnum> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, LanguageEnum parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.name());
    }

    @Override
    public LanguageEnum getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return LanguageEnum.valueOf(rs.getString(columnName));
    }

    @Override
    public LanguageEnum getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return LanguageEnum.valueOf(rs.getString(columnIndex));
    }

    @Override
    public LanguageEnum getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return LanguageEnum.valueOf(cs.getString(columnIndex));
    }
}
