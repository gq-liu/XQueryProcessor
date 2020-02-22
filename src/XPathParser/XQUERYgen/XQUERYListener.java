package XQUERYgen;// Generated from /Users/liuguoqiang/Study/20win/cse232b/cse232bProject/src/XPathParser/XQUERY.g4 by ANTLR 4.7.2
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link XQUERYParser}.
 */
public interface XQUERYListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by the {@code xqAp}
	 * labeled alternative in {@link XQUERYParser#xq}.
	 * @param ctx the parse tree
	 */
	void enterXqAp(XQUERYParser.XqApContext ctx);
	/**
	 * Exit a parse tree produced by the {@code xqAp}
	 * labeled alternative in {@link XQUERYParser#xq}.
	 * @param ctx the parse tree
	 */
	void exitXqAp(XQUERYParser.XqApContext ctx);
	/**
	 * Enter a parse tree produced by the {@code xqVar}
	 * labeled alternative in {@link XQUERYParser#xq}.
	 * @param ctx the parse tree
	 */
	void enterXqVar(XQUERYParser.XqVarContext ctx);
	/**
	 * Exit a parse tree produced by the {@code xqVar}
	 * labeled alternative in {@link XQUERYParser#xq}.
	 * @param ctx the parse tree
	 */
	void exitXqVar(XQUERYParser.XqVarContext ctx);
	/**
	 * Enter a parse tree produced by the {@code xqFLWR}
	 * labeled alternative in {@link XQUERYParser#xq}.
	 * @param ctx the parse tree
	 */
	void enterXqFLWR(XQUERYParser.XqFLWRContext ctx);
	/**
	 * Exit a parse tree produced by the {@code xqFLWR}
	 * labeled alternative in {@link XQUERYParser#xq}.
	 * @param ctx the parse tree
	 */
	void exitXqFLWR(XQUERYParser.XqFLWRContext ctx);
	/**
	 * Enter a parse tree produced by the {@code xqSingleSlash}
	 * labeled alternative in {@link XQUERYParser#xq}.
	 * @param ctx the parse tree
	 */
	void enterXqSingleSlash(XQUERYParser.XqSingleSlashContext ctx);
	/**
	 * Exit a parse tree produced by the {@code xqSingleSlash}
	 * labeled alternative in {@link XQUERYParser#xq}.
	 * @param ctx the parse tree
	 */
	void exitXqSingleSlash(XQUERYParser.XqSingleSlashContext ctx);
	/**
	 * Enter a parse tree produced by the {@code xqDoubleSlash}
	 * labeled alternative in {@link XQUERYParser#xq}.
	 * @param ctx the parse tree
	 */
	void enterXqDoubleSlash(XQUERYParser.XqDoubleSlashContext ctx);
	/**
	 * Exit a parse tree produced by the {@code xqDoubleSlash}
	 * labeled alternative in {@link XQUERYParser#xq}.
	 * @param ctx the parse tree
	 */
	void exitXqDoubleSlash(XQUERYParser.XqDoubleSlashContext ctx);
	/**
	 * Enter a parse tree produced by the {@code xqParentheses}
	 * labeled alternative in {@link XQUERYParser#xq}.
	 * @param ctx the parse tree
	 */
	void enterXqParentheses(XQUERYParser.XqParenthesesContext ctx);
	/**
	 * Exit a parse tree produced by the {@code xqParentheses}
	 * labeled alternative in {@link XQUERYParser#xq}.
	 * @param ctx the parse tree
	 */
	void exitXqParentheses(XQUERYParser.XqParenthesesContext ctx);
	/**
	 * Enter a parse tree produced by the {@code xqStringConstant}
	 * labeled alternative in {@link XQUERYParser#xq}.
	 * @param ctx the parse tree
	 */
	void enterXqStringConstant(XQUERYParser.XqStringConstantContext ctx);
	/**
	 * Exit a parse tree produced by the {@code xqStringConstant}
	 * labeled alternative in {@link XQUERYParser#xq}.
	 * @param ctx the parse tree
	 */
	void exitXqStringConstant(XQUERYParser.XqStringConstantContext ctx);
	/**
	 * Enter a parse tree produced by the {@code xqLet}
	 * labeled alternative in {@link XQUERYParser#xq}.
	 * @param ctx the parse tree
	 */
	void enterXqLet(XQUERYParser.XqLetContext ctx);
	/**
	 * Exit a parse tree produced by the {@code xqLet}
	 * labeled alternative in {@link XQUERYParser#xq}.
	 * @param ctx the parse tree
	 */
	void exitXqLet(XQUERYParser.XqLetContext ctx);
	/**
	 * Enter a parse tree produced by the {@code xqTag}
	 * labeled alternative in {@link XQUERYParser#xq}.
	 * @param ctx the parse tree
	 */
	void enterXqTag(XQUERYParser.XqTagContext ctx);
	/**
	 * Exit a parse tree produced by the {@code xqTag}
	 * labeled alternative in {@link XQUERYParser#xq}.
	 * @param ctx the parse tree
	 */
	void exitXqTag(XQUERYParser.XqTagContext ctx);
	/**
	 * Enter a parse tree produced by the {@code xqComma}
	 * labeled alternative in {@link XQUERYParser#xq}.
	 * @param ctx the parse tree
	 */
	void enterXqComma(XQUERYParser.XqCommaContext ctx);
	/**
	 * Exit a parse tree produced by the {@code xqComma}
	 * labeled alternative in {@link XQUERYParser#xq}.
	 * @param ctx the parse tree
	 */
	void exitXqComma(XQUERYParser.XqCommaContext ctx);
	/**
	 * Enter a parse tree produced by {@link XQUERYParser#forClause}.
	 * @param ctx the parse tree
	 */
	void enterForClause(XQUERYParser.ForClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link XQUERYParser#forClause}.
	 * @param ctx the parse tree
	 */
	void exitForClause(XQUERYParser.ForClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link XQUERYParser#letClause}.
	 * @param ctx the parse tree
	 */
	void enterLetClause(XQUERYParser.LetClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link XQUERYParser#letClause}.
	 * @param ctx the parse tree
	 */
	void exitLetClause(XQUERYParser.LetClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link XQUERYParser#whereClause}.
	 * @param ctx the parse tree
	 */
	void enterWhereClause(XQUERYParser.WhereClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link XQUERYParser#whereClause}.
	 * @param ctx the parse tree
	 */
	void exitWhereClause(XQUERYParser.WhereClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link XQUERYParser#returnClause}.
	 * @param ctx the parse tree
	 */
	void enterReturnClause(XQUERYParser.ReturnClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link XQUERYParser#returnClause}.
	 * @param ctx the parse tree
	 */
	void exitReturnClause(XQUERYParser.ReturnClauseContext ctx);
	/**
	 * Enter a parse tree produced by the {@code condEmpty}
	 * labeled alternative in {@link XQUERYParser#cond}.
	 * @param ctx the parse tree
	 */
	void enterCondEmpty(XQUERYParser.CondEmptyContext ctx);
	/**
	 * Exit a parse tree produced by the {@code condEmpty}
	 * labeled alternative in {@link XQUERYParser#cond}.
	 * @param ctx the parse tree
	 */
	void exitCondEmpty(XQUERYParser.CondEmptyContext ctx);
	/**
	 * Enter a parse tree produced by the {@code condEq}
	 * labeled alternative in {@link XQUERYParser#cond}.
	 * @param ctx the parse tree
	 */
	void enterCondEq(XQUERYParser.CondEqContext ctx);
	/**
	 * Exit a parse tree produced by the {@code condEq}
	 * labeled alternative in {@link XQUERYParser#cond}.
	 * @param ctx the parse tree
	 */
	void exitCondEq(XQUERYParser.CondEqContext ctx);
	/**
	 * Enter a parse tree produced by the {@code condIs}
	 * labeled alternative in {@link XQUERYParser#cond}.
	 * @param ctx the parse tree
	 */
	void enterCondIs(XQUERYParser.CondIsContext ctx);
	/**
	 * Exit a parse tree produced by the {@code condIs}
	 * labeled alternative in {@link XQUERYParser#cond}.
	 * @param ctx the parse tree
	 */
	void exitCondIs(XQUERYParser.CondIsContext ctx);
	/**
	 * Enter a parse tree produced by the {@code condSatisfy}
	 * labeled alternative in {@link XQUERYParser#cond}.
	 * @param ctx the parse tree
	 */
	void enterCondSatisfy(XQUERYParser.CondSatisfyContext ctx);
	/**
	 * Exit a parse tree produced by the {@code condSatisfy}
	 * labeled alternative in {@link XQUERYParser#cond}.
	 * @param ctx the parse tree
	 */
	void exitCondSatisfy(XQUERYParser.CondSatisfyContext ctx);
	/**
	 * Enter a parse tree produced by the {@code condAND}
	 * labeled alternative in {@link XQUERYParser#cond}.
	 * @param ctx the parse tree
	 */
	void enterCondAND(XQUERYParser.CondANDContext ctx);
	/**
	 * Exit a parse tree produced by the {@code condAND}
	 * labeled alternative in {@link XQUERYParser#cond}.
	 * @param ctx the parse tree
	 */
	void exitCondAND(XQUERYParser.CondANDContext ctx);
	/**
	 * Enter a parse tree produced by the {@code condOR}
	 * labeled alternative in {@link XQUERYParser#cond}.
	 * @param ctx the parse tree
	 */
	void enterCondOR(XQUERYParser.CondORContext ctx);
	/**
	 * Exit a parse tree produced by the {@code condOR}
	 * labeled alternative in {@link XQUERYParser#cond}.
	 * @param ctx the parse tree
	 */
	void exitCondOR(XQUERYParser.CondORContext ctx);
	/**
	 * Enter a parse tree produced by the {@code condComma}
	 * labeled alternative in {@link XQUERYParser#cond}.
	 * @param ctx the parse tree
	 */
	void enterCondComma(XQUERYParser.CondCommaContext ctx);
	/**
	 * Exit a parse tree produced by the {@code condComma}
	 * labeled alternative in {@link XQUERYParser#cond}.
	 * @param ctx the parse tree
	 */
	void exitCondComma(XQUERYParser.CondCommaContext ctx);
	/**
	 * Enter a parse tree produced by the {@code condNOT}
	 * labeled alternative in {@link XQUERYParser#cond}.
	 * @param ctx the parse tree
	 */
	void enterCondNOT(XQUERYParser.CondNOTContext ctx);
	/**
	 * Exit a parse tree produced by the {@code condNOT}
	 * labeled alternative in {@link XQUERYParser#cond}.
	 * @param ctx the parse tree
	 */
	void exitCondNOT(XQUERYParser.CondNOTContext ctx);
	/**
	 * Enter a parse tree produced by the {@code apSingle}
	 * labeled alternative in {@link XQUERYParser#ap}.
	 * @param ctx the parse tree
	 */
	void enterApSingle(XQUERYParser.ApSingleContext ctx);
	/**
	 * Exit a parse tree produced by the {@code apSingle}
	 * labeled alternative in {@link XQUERYParser#ap}.
	 * @param ctx the parse tree
	 */
	void exitApSingle(XQUERYParser.ApSingleContext ctx);
	/**
	 * Enter a parse tree produced by the {@code apDouble}
	 * labeled alternative in {@link XQUERYParser#ap}.
	 * @param ctx the parse tree
	 */
	void enterApDouble(XQUERYParser.ApDoubleContext ctx);
	/**
	 * Exit a parse tree produced by the {@code apDouble}
	 * labeled alternative in {@link XQUERYParser#ap}.
	 * @param ctx the parse tree
	 */
	void exitApDouble(XQUERYParser.ApDoubleContext ctx);
	/**
	 * Enter a parse tree produced by {@link XQUERYParser#doc}.
	 * @param ctx the parse tree
	 */
	void enterDoc(XQUERYParser.DocContext ctx);
	/**
	 * Exit a parse tree produced by {@link XQUERYParser#doc}.
	 * @param ctx the parse tree
	 */
	void exitDoc(XQUERYParser.DocContext ctx);
	/**
	 * Enter a parse tree produced by the {@code rpSelf}
	 * labeled alternative in {@link XQUERYParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterRpSelf(XQUERYParser.RpSelfContext ctx);
	/**
	 * Exit a parse tree produced by the {@code rpSelf}
	 * labeled alternative in {@link XQUERYParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitRpSelf(XQUERYParser.RpSelfContext ctx);
	/**
	 * Enter a parse tree produced by the {@code rpText}
	 * labeled alternative in {@link XQUERYParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterRpText(XQUERYParser.RpTextContext ctx);
	/**
	 * Exit a parse tree produced by the {@code rpText}
	 * labeled alternative in {@link XQUERYParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitRpText(XQUERYParser.RpTextContext ctx);
	/**
	 * Enter a parse tree produced by the {@code rpSingleSlash}
	 * labeled alternative in {@link XQUERYParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterRpSingleSlash(XQUERYParser.RpSingleSlashContext ctx);
	/**
	 * Exit a parse tree produced by the {@code rpSingleSlash}
	 * labeled alternative in {@link XQUERYParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitRpSingleSlash(XQUERYParser.RpSingleSlashContext ctx);
	/**
	 * Enter a parse tree produced by the {@code rpChildren}
	 * labeled alternative in {@link XQUERYParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterRpChildren(XQUERYParser.RpChildrenContext ctx);
	/**
	 * Exit a parse tree produced by the {@code rpChildren}
	 * labeled alternative in {@link XQUERYParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitRpChildren(XQUERYParser.RpChildrenContext ctx);
	/**
	 * Enter a parse tree produced by the {@code rpAttri}
	 * labeled alternative in {@link XQUERYParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterRpAttri(XQUERYParser.RpAttriContext ctx);
	/**
	 * Exit a parse tree produced by the {@code rpAttri}
	 * labeled alternative in {@link XQUERYParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitRpAttri(XQUERYParser.RpAttriContext ctx);
	/**
	 * Enter a parse tree produced by the {@code rpTag}
	 * labeled alternative in {@link XQUERYParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterRpTag(XQUERYParser.RpTagContext ctx);
	/**
	 * Exit a parse tree produced by the {@code rpTag}
	 * labeled alternative in {@link XQUERYParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitRpTag(XQUERYParser.RpTagContext ctx);
	/**
	 * Enter a parse tree produced by the {@code rpParent}
	 * labeled alternative in {@link XQUERYParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterRpParent(XQUERYParser.RpParentContext ctx);
	/**
	 * Exit a parse tree produced by the {@code rpParent}
	 * labeled alternative in {@link XQUERYParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitRpParent(XQUERYParser.RpParentContext ctx);
	/**
	 * Enter a parse tree produced by the {@code rpParentheses}
	 * labeled alternative in {@link XQUERYParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterRpParentheses(XQUERYParser.RpParenthesesContext ctx);
	/**
	 * Exit a parse tree produced by the {@code rpParentheses}
	 * labeled alternative in {@link XQUERYParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitRpParentheses(XQUERYParser.RpParenthesesContext ctx);
	/**
	 * Enter a parse tree produced by the {@code rpCondition}
	 * labeled alternative in {@link XQUERYParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterRpCondition(XQUERYParser.RpConditionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code rpCondition}
	 * labeled alternative in {@link XQUERYParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitRpCondition(XQUERYParser.RpConditionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code rpComma}
	 * labeled alternative in {@link XQUERYParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterRpComma(XQUERYParser.RpCommaContext ctx);
	/**
	 * Exit a parse tree produced by the {@code rpComma}
	 * labeled alternative in {@link XQUERYParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitRpComma(XQUERYParser.RpCommaContext ctx);
	/**
	 * Enter a parse tree produced by the {@code rpDoubleSlash}
	 * labeled alternative in {@link XQUERYParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterRpDoubleSlash(XQUERYParser.RpDoubleSlashContext ctx);
	/**
	 * Exit a parse tree produced by the {@code rpDoubleSlash}
	 * labeled alternative in {@link XQUERYParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitRpDoubleSlash(XQUERYParser.RpDoubleSlashContext ctx);
	/**
	 * Enter a parse tree produced by the {@code filterIs}
	 * labeled alternative in {@link XQUERYParser#f}.
	 * @param ctx the parse tree
	 */
	void enterFilterIs(XQUERYParser.FilterIsContext ctx);
	/**
	 * Exit a parse tree produced by the {@code filterIs}
	 * labeled alternative in {@link XQUERYParser#f}.
	 * @param ctx the parse tree
	 */
	void exitFilterIs(XQUERYParser.FilterIsContext ctx);
	/**
	 * Enter a parse tree produced by the {@code filterRp}
	 * labeled alternative in {@link XQUERYParser#f}.
	 * @param ctx the parse tree
	 */
	void enterFilterRp(XQUERYParser.FilterRpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code filterRp}
	 * labeled alternative in {@link XQUERYParser#f}.
	 * @param ctx the parse tree
	 */
	void exitFilterRp(XQUERYParser.FilterRpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code filterParentheses}
	 * labeled alternative in {@link XQUERYParser#f}.
	 * @param ctx the parse tree
	 */
	void enterFilterParentheses(XQUERYParser.FilterParenthesesContext ctx);
	/**
	 * Exit a parse tree produced by the {@code filterParentheses}
	 * labeled alternative in {@link XQUERYParser#f}.
	 * @param ctx the parse tree
	 */
	void exitFilterParentheses(XQUERYParser.FilterParenthesesContext ctx);
	/**
	 * Enter a parse tree produced by the {@code filterEq}
	 * labeled alternative in {@link XQUERYParser#f}.
	 * @param ctx the parse tree
	 */
	void enterFilterEq(XQUERYParser.FilterEqContext ctx);
	/**
	 * Exit a parse tree produced by the {@code filterEq}
	 * labeled alternative in {@link XQUERYParser#f}.
	 * @param ctx the parse tree
	 */
	void exitFilterEq(XQUERYParser.FilterEqContext ctx);
	/**
	 * Enter a parse tree produced by the {@code filterOR}
	 * labeled alternative in {@link XQUERYParser#f}.
	 * @param ctx the parse tree
	 */
	void enterFilterOR(XQUERYParser.FilterORContext ctx);
	/**
	 * Exit a parse tree produced by the {@code filterOR}
	 * labeled alternative in {@link XQUERYParser#f}.
	 * @param ctx the parse tree
	 */
	void exitFilterOR(XQUERYParser.FilterORContext ctx);
	/**
	 * Enter a parse tree produced by the {@code filterAND}
	 * labeled alternative in {@link XQUERYParser#f}.
	 * @param ctx the parse tree
	 */
	void enterFilterAND(XQUERYParser.FilterANDContext ctx);
	/**
	 * Exit a parse tree produced by the {@code filterAND}
	 * labeled alternative in {@link XQUERYParser#f}.
	 * @param ctx the parse tree
	 */
	void exitFilterAND(XQUERYParser.FilterANDContext ctx);
	/**
	 * Enter a parse tree produced by the {@code filterNOT}
	 * labeled alternative in {@link XQUERYParser#f}.
	 * @param ctx the parse tree
	 */
	void enterFilterNOT(XQUERYParser.FilterNOTContext ctx);
	/**
	 * Exit a parse tree produced by the {@code filterNOT}
	 * labeled alternative in {@link XQUERYParser#f}.
	 * @param ctx the parse tree
	 */
	void exitFilterNOT(XQUERYParser.FilterNOTContext ctx);
	/**
	 * Enter a parse tree produced by {@link XQUERYParser#tagName}.
	 * @param ctx the parse tree
	 */
	void enterTagName(XQUERYParser.TagNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link XQUERYParser#tagName}.
	 * @param ctx the parse tree
	 */
	void exitTagName(XQUERYParser.TagNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link XQUERYParser#attName}.
	 * @param ctx the parse tree
	 */
	void enterAttName(XQUERYParser.AttNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link XQUERYParser#attName}.
	 * @param ctx the parse tree
	 */
	void exitAttName(XQUERYParser.AttNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link XQUERYParser#filename}.
	 * @param ctx the parse tree
	 */
	void enterFilename(XQUERYParser.FilenameContext ctx);
	/**
	 * Exit a parse tree produced by {@link XQUERYParser#filename}.
	 * @param ctx the parse tree
	 */
	void exitFilename(XQUERYParser.FilenameContext ctx);
}