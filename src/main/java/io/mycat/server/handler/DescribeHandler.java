package io.mycat.server.handler;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLName;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.expr.SQLIdentifierExpr;
import com.alibaba.druid.sql.ast.expr.SQLPropertyExpr;
import com.alibaba.druid.sql.ast.statement.SQLShowTablesStatement;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlExplainStatement;
import com.alibaba.druid.sql.visitor.functions.If;
import io.mycat.backend.mysql.nio.handler.SingleNodeHandler;
import io.mycat.config.ErrorCode;
import io.mycat.config.model.TableConfig;
import io.mycat.route.RouteResultset;
import io.mycat.route.factory.RouteStrategyFactory;
import io.mycat.route.util.RouterUtil;
import io.mycat.server.ServerConnection;
import io.mycat.server.parser.ServerParse;
import io.mycat.server.util.SchemaUtil;
import io.mycat.server.util.SchemaUtil.SchemaInfo;
import io.mycat.util.StringUtil;

import java.sql.SQLException;

public class DescribeHandler {
	public static void handle(String stmt, ServerConnection c) {
		try {
			SQLStatement statement = RouteStrategyFactory.getRouteStrategy().parserSQL(stmt);
			MySqlExplainStatement describeStatement = (MySqlExplainStatement) statement;
			SchemaInfo schemaInfo = SchemaUtil.getSchemaInfo(c.getUser(), c.getSchema(), describeStatement.getTableName());
			c.routeSystemInfoAndExecuteSQL(RouterUtil.removeSchema(stmt, schemaInfo.schema), schemaInfo, ServerParse.DESCRIBE);
		} catch (Exception e) {
			c.writeErrMessage(ErrorCode.ER_PARSE_ERROR, e.toString());
			return;
		}
	}
}
