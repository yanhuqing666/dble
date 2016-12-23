package io.mycat.route.parser.druid.impl.ddl;

import java.sql.SQLNonTransientException;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLDropIndexStatement;

import io.mycat.config.model.SchemaConfig;
import io.mycat.route.RouteResultset;
import io.mycat.route.parser.druid.MycatSchemaStatVisitor;
import io.mycat.route.parser.druid.impl.DefaultDruidParser;
import io.mycat.route.util.RouterUtil;
import io.mycat.server.util.SchemaUtil;
import io.mycat.server.util.SchemaUtil.SchemaInfo;

/**
 * 
 * @author huqing.yan
 *
 */
public class DruidDropIndexParser extends DefaultDruidParser {
	@Override
	public void visitorParse(RouteResultset rrs, SQLStatement stmt, MycatSchemaStatVisitor visitor)
			throws SQLNonTransientException {

	}

	@Override
	public void statementParse(SchemaConfig schema, RouteResultset rrs, SQLStatement stmt)
			throws SQLNonTransientException {
		SQLDropIndexStatement dropStmt = (SQLDropIndexStatement)stmt;
		SchemaInfo schemaInfo = SchemaUtil.getSchemaInfo(schema.getName(), dropStmt.getTableName());
		if (schemaInfo == null) {
			String msg = "No MyCAT Database is selected Or defined, sql:" + stmt;
			throw new SQLNonTransientException(msg);
		}
		rrs = RouterUtil.routeToDDLNode(schemaInfo, rrs, ctx.getSql());
	}
}