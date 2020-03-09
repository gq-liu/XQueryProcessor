package XQUERYgen;// Generated from /Users/liuguoqiang/Study/20win/cse232b/cse232bProject/src/XQuery/XQUERY.g4 by ANTLR 4.7.2
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link XQUERYParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface XQUERYVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by the {@code xqAp}
	 * labeled alternative in {@link XQUERYParser#xq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitXqAp(XQUERYParser.XqApContext ctx);
	/**
	 * Visit a parse tree produced by the {@code xqVar}
	 * labeled alternative in {@link XQUERYParser#xq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitXqVar(XQUERYParser.XqVarContext ctx);
	/**
	 * Visit a parse tree produced by the {@code xqJoin}
	 * labeled alternative in {@link XQUERYParser#xq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitXqJoin(XQUERYParser.XqJoinContext ctx);
	/**
	 * Visit a parse tree produced by the {@code xqFLWR}
	 * labeled alternative in {@link XQUERYParser#xq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitXqFLWR(XQUERYParser.XqFLWRContext ctx);
	/**
	 * Visit a parse tree produced by the {@code xqSingleSlash}
	 * labeled alternative in {@link XQUERYParser#xq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitXqSingleSlash(XQUERYParser.XqSingleSlashContext ctx);
	/**
	 * Visit a parse tree produced by the {@code xqDoubleSlash}
	 * labeled alternative in {@link XQUERYParser#xq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitXqDoubleSlash(XQUERYParser.XqDoubleSlashContext ctx);
	/**
	 * Visit a parse tree produced by the {@code xqParentheses}
	 * labeled alternative in {@link XQUERYParser#xq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitXqParentheses(XQUERYParser.XqParenthesesContext ctx);
	/**
	 * Visit a parse tree produced by the {@code xqStringConstant}
	 * labeled alternative in {@link XQUERYParser#xq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitXqStringConstant(XQUERYParser.XqStringConstantContext ctx);
	/**
	 * Visit a parse tree produced by the {@code xqLet}
	 * labeled alternative in {@link XQUERYParser#xq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitXqLet(XQUERYParser.XqLetContext ctx);
	/**
	 * Visit a parse tree produced by the {@code xqTag}
	 * labeled alternative in {@link XQUERYParser#xq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitXqTag(XQUERYParser.XqTagContext ctx);
	/**
	 * Visit a parse tree produced by the {@code xqComma}
	 * labeled alternative in {@link XQUERYParser#xq}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitXqComma(XQUERYParser.XqCommaContext ctx);
	/**
	 * Visit a parse tree produced by {@link XQUERYParser#forClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForClause(XQUERYParser.ForClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link XQUERYParser#letClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLetClause(XQUERYParser.LetClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link XQUERYParser#whereClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhereClause(XQUERYParser.WhereClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link XQUERYParser#returnClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnClause(XQUERYParser.ReturnClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link XQUERYParser#joinClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJoinClause(XQUERYParser.JoinClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link XQUERYParser#joinKeys}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJoinKeys(XQUERYParser.JoinKeysContext ctx);
	/**
	 * Visit a parse tree produced by the {@code condEmpty}
	 * labeled alternative in {@link XQUERYParser#cond}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCondEmpty(XQUERYParser.CondEmptyContext ctx);
	/**
	 * Visit a parse tree produced by the {@code condEq}
	 * labeled alternative in {@link XQUERYParser#cond}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCondEq(XQUERYParser.CondEqContext ctx);
	/**
	 * Visit a parse tree produced by the {@code condIs}
	 * labeled alternative in {@link XQUERYParser#cond}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCondIs(XQUERYParser.CondIsContext ctx);
	/**
	 * Visit a parse tree produced by the {@code condSatisfy}
	 * labeled alternative in {@link XQUERYParser#cond}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCondSatisfy(XQUERYParser.CondSatisfyContext ctx);
	/**
	 * Visit a parse tree produced by the {@code condAND}
	 * labeled alternative in {@link XQUERYParser#cond}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCondAND(XQUERYParser.CondANDContext ctx);
	/**
	 * Visit a parse tree produced by the {@code condOR}
	 * labeled alternative in {@link XQUERYParser#cond}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCondOR(XQUERYParser.CondORContext ctx);
	/**
	 * Visit a parse tree produced by the {@code condComma}
	 * labeled alternative in {@link XQUERYParser#cond}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCondComma(XQUERYParser.CondCommaContext ctx);
	/**
	 * Visit a parse tree produced by the {@code condNOT}
	 * labeled alternative in {@link XQUERYParser#cond}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCondNOT(XQUERYParser.CondNOTContext ctx);
	/**
	 * Visit a parse tree produced by the {@code apSingle}
	 * labeled alternative in {@link XQUERYParser#ap}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitApSingle(XQUERYParser.ApSingleContext ctx);
	/**
	 * Visit a parse tree produced by the {@code apDouble}
	 * labeled alternative in {@link XQUERYParser#ap}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitApDouble(XQUERYParser.ApDoubleContext ctx);
	/**
	 * Visit a parse tree produced by {@link XQUERYParser#doc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDoc(XQUERYParser.DocContext ctx);
	/**
	 * Visit a parse tree produced by the {@code rpSelf}
	 * labeled alternative in {@link XQUERYParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRpSelf(XQUERYParser.RpSelfContext ctx);
	/**
	 * Visit a parse tree produced by the {@code rpText}
	 * labeled alternative in {@link XQUERYParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRpText(XQUERYParser.RpTextContext ctx);
	/**
	 * Visit a parse tree produced by the {@code rpSingleSlash}
	 * labeled alternative in {@link XQUERYParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRpSingleSlash(XQUERYParser.RpSingleSlashContext ctx);
	/**
	 * Visit a parse tree produced by the {@code rpChildren}
	 * labeled alternative in {@link XQUERYParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRpChildren(XQUERYParser.RpChildrenContext ctx);
	/**
	 * Visit a parse tree produced by the {@code rpAttri}
	 * labeled alternative in {@link XQUERYParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRpAttri(XQUERYParser.RpAttriContext ctx);
	/**
	 * Visit a parse tree produced by the {@code rpTag}
	 * labeled alternative in {@link XQUERYParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRpTag(XQUERYParser.RpTagContext ctx);
	/**
	 * Visit a parse tree produced by the {@code rpParent}
	 * labeled alternative in {@link XQUERYParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRpParent(XQUERYParser.RpParentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code rpParentheses}
	 * labeled alternative in {@link XQUERYParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRpParentheses(XQUERYParser.RpParenthesesContext ctx);
	/**
	 * Visit a parse tree produced by the {@code rpCondition}
	 * labeled alternative in {@link XQUERYParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRpCondition(XQUERYParser.RpConditionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code rpComma}
	 * labeled alternative in {@link XQUERYParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRpComma(XQUERYParser.RpCommaContext ctx);
	/**
	 * Visit a parse tree produced by the {@code rpDoubleSlash}
	 * labeled alternative in {@link XQUERYParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRpDoubleSlash(XQUERYParser.RpDoubleSlashContext ctx);
	/**
	 * Visit a parse tree produced by the {@code filterIs}
	 * labeled alternative in {@link XQUERYParser#f}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFilterIs(XQUERYParser.FilterIsContext ctx);
	/**
	 * Visit a parse tree produced by the {@code filterRp}
	 * labeled alternative in {@link XQUERYParser#f}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFilterRp(XQUERYParser.FilterRpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code filterParentheses}
	 * labeled alternative in {@link XQUERYParser#f}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFilterParentheses(XQUERYParser.FilterParenthesesContext ctx);
	/**
	 * Visit a parse tree produced by the {@code filterEq}
	 * labeled alternative in {@link XQUERYParser#f}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFilterEq(XQUERYParser.FilterEqContext ctx);
	/**
	 * Visit a parse tree produced by the {@code filterOR}
	 * labeled alternative in {@link XQUERYParser#f}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFilterOR(XQUERYParser.FilterORContext ctx);
	/**
	 * Visit a parse tree produced by the {@code filterAND}
	 * labeled alternative in {@link XQUERYParser#f}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFilterAND(XQUERYParser.FilterANDContext ctx);
	/**
	 * Visit a parse tree produced by the {@code filterNOT}
	 * labeled alternative in {@link XQUERYParser#f}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFilterNOT(XQUERYParser.FilterNOTContext ctx);
	/**
	 * Visit a parse tree produced by {@link XQUERYParser#tagName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTagName(XQUERYParser.TagNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link XQUERYParser#attName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAttName(XQUERYParser.AttNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link XQUERYParser#filename}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFilename(XQUERYParser.FilenameContext ctx);
}