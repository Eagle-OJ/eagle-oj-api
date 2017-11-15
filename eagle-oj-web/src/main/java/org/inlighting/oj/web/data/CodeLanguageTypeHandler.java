package org.inlighting.oj.web.data;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.inlighting.oj.judge.config.CodeLanguageEnum;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Smith
 **/
public class CodeLanguageTypeHandler extends BaseTypeHandler<CodeLanguageEnum> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, CodeLanguageEnum parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.name());
    }

    @Override
    public CodeLanguageEnum getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return CodeLanguageEnum.valueOf(rs.getString(columnName));
    }

    @Override
    public CodeLanguageEnum getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return CodeLanguageEnum.valueOf(rs.getString(columnIndex));
    }

    @Override
    public CodeLanguageEnum getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return CodeLanguageEnum.valueOf(cs.getString(columnIndex));
    }
}
