// Generated from /Users/tianyusun/Desktop/20winter/CSE232B/CSE232WI20PROJECT/cse232bProject/src/XPathParser/XPATH.g4 by ANTLR 4.7.2
package XPathParser.XPATHgen;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link XPATHParser}.
 */
public interface XPATHListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by the {@code apSingle}
	 * labeled alternative in {@link XPATHParser#ap}.
	 * @param ctx the parse tree
	 */
	void enterApSingle(XPATHParser.ApSingleContext ctx);
	/**
	 * Exit a parse tree produced by the {@code apSingle}
	 * labeled alternative in {@link XPATHParser#ap}.
	 * @param ctx the parse tree
	 */
	void exitApSingle(XPATHParser.ApSingleContext ctx);
	/**
	 * Enter a parse tree produced by the {@code apDouble}
	 * labeled alternative in {@link XPATHParser#ap}.
	 * @param ctx the parse tree
	 */
	void enterApDouble(XPATHParser.ApDoubleContext ctx);
	/**
	 * Exit a parse tree produced by the {@code apDouble}
	 * labeled alternative in {@link XPATHParser#ap}.
	 * @param ctx the parse tree
	 */
	void exitApDouble(XPATHParser.ApDoubleContext ctx);
	/**
	 * Enter a parse tree produced by {@link XPATHParser#doc}.
	 * @param ctx the parse tree
	 */
	void enterDoc(XPATHParser.DocContext ctx);
	/**
	 * Exit a parse tree produced by {@link XPATHParser#doc}.
	 * @param ctx the parse tree
	 */
	void exitDoc(XPATHParser.DocContext ctx);
	/**
	 * Enter a parse tree produced by the {@code rpSelf}
	 * labeled alternative in {@link XPATHParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterRpSelf(XPATHParser.RpSelfContext ctx);
	/**
	 * Exit a parse tree produced by the {@code rpSelf}
	 * labeled alternative in {@link XPATHParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitRpSelf(XPATHParser.RpSelfContext ctx);
	/**
	 * Enter a parse tree produced by the {@code rpText}
	 * labeled alternative in {@link XPATHParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterRpText(XPATHParser.RpTextContext ctx);
	/**
	 * Exit a parse tree produced by the {@code rpText}
	 * labeled alternative in {@link XPATHParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitRpText(XPATHParser.RpTextContext ctx);
	/**
	 * Enter a parse tree produced by the {@code rpSingleSlash}
	 * labeled alternative in {@link XPATHParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterRpSingleSlash(XPATHParser.RpSingleSlashContext ctx);
	/**
	 * Exit a parse tree produced by the {@code rpSingleSlash}
	 * labeled alternative in {@link XPATHParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitRpSingleSlash(XPATHParser.RpSingleSlashContext ctx);
	/**
	 * Enter a parse tree produced by the {@code rpChildren}
	 * labeled alternative in {@link XPATHParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterRpChildren(XPATHParser.RpChildrenContext ctx);
	/**
	 * Exit a parse tree produced by the {@code rpChildren}
	 * labeled alternative in {@link XPATHParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitRpChildren(XPATHParser.RpChildrenContext ctx);
	/**
	 * Enter a parse tree produced by the {@code rpAttri}
	 * labeled alternative in {@link XPATHParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterRpAttri(XPATHParser.RpAttriContext ctx);
	/**
	 * Exit a parse tree produced by the {@code rpAttri}
	 * labeled alternative in {@link XPATHParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitRpAttri(XPATHParser.RpAttriContext ctx);
	/**
	 * Enter a parse tree produced by the {@code rpTag}
	 * labeled alternative in {@link XPATHParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterRpTag(XPATHParser.RpTagContext ctx);
	/**
	 * Exit a parse tree produced by the {@code rpTag}
	 * labeled alternative in {@link XPATHParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitRpTag(XPATHParser.RpTagContext ctx);
	/**
	 * Enter a parse tree produced by the {@code rpParent}
	 * labeled alternative in {@link XPATHParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterRpParent(XPATHParser.RpParentContext ctx);
	/**
	 * Exit a parse tree produced by the {@code rpParent}
	 * labeled alternative in {@link XPATHParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitRpParent(XPATHParser.RpParentContext ctx);
	/**
	 * Enter a parse tree produced by the {@code rpParentheses}
	 * labeled alternative in {@link XPATHParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterRpParentheses(XPATHParser.RpParenthesesContext ctx);
	/**
	 * Exit a parse tree produced by the {@code rpParentheses}
	 * labeled alternative in {@link XPATHParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitRpParentheses(XPATHParser.RpParenthesesContext ctx);
	/**
	 * Enter a parse tree produced by the {@code rpCondition}
	 * labeled alternative in {@link XPATHParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterRpCondition(XPATHParser.RpConditionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code rpCondition}
	 * labeled alternative in {@link XPATHParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitRpCondition(XPATHParser.RpConditionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code rpComma}
	 * labeled alternative in {@link XPATHParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterRpComma(XPATHParser.RpCommaContext ctx);
	/**
	 * Exit a parse tree produced by the {@code rpComma}
	 * labeled alternative in {@link XPATHParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitRpComma(XPATHParser.RpCommaContext ctx);
	/**
	 * Enter a parse tree produced by the {@code rpDoubleSlash}
	 * labeled alternative in {@link XPATHParser#rp}.
	 * @param ctx the parse tree
	 */
	void enterRpDoubleSlash(XPATHParser.RpDoubleSlashContext ctx);
	/**
	 * Exit a parse tree produced by the {@code rpDoubleSlash}
	 * labeled alternative in {@link XPATHParser#rp}.
	 * @param ctx the parse tree
	 */
	void exitRpDoubleSlash(XPATHParser.RpDoubleSlashContext ctx);
	/**
	 * Enter a parse tree produced by the {@code filterIs}
	 * labeled alternative in {@link XPATHParser#f}.
	 * @param ctx the parse tree
	 */
	void enterFilterIs(XPATHParser.FilterIsContext ctx);
	/**
	 * Exit a parse tree produced by the {@code filterIs}
	 * labeled alternative in {@link XPATHParser#f}.
	 * @param ctx the parse tree
	 */
	void exitFilterIs(XPATHParser.FilterIsContext ctx);
	/**
	 * Enter a parse tree produced by the {@code filterRp}
	 * labeled alternative in {@link XPATHParser#f}.
	 * @param ctx the parse tree
	 */
	void enterFilterRp(XPATHParser.FilterRpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code filterRp}
	 * labeled alternative in {@link XPATHParser#f}.
	 * @param ctx the parse tree
	 */
	void exitFilterRp(XPATHParser.FilterRpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code filterParentheses}
	 * labeled alternative in {@link XPATHParser#f}.
	 * @param ctx the parse tree
	 */
	void enterFilterParentheses(XPATHParser.FilterParenthesesContext ctx);
	/**
	 * Exit a parse tree produced by the {@code filterParentheses}
	 * labeled alternative in {@link XPATHParser#f}.
	 * @param ctx the parse tree
	 */
	void exitFilterParentheses(XPATHParser.FilterParenthesesContext ctx);
	/**
	 * Enter a parse tree produced by the {@code filterEq}
	 * labeled alternative in {@link XPATHParser#f}.
	 * @param ctx the parse tree
	 */
	void enterFilterEq(XPATHParser.FilterEqContext ctx);
	/**
	 * Exit a parse tree produced by the {@code filterEq}
	 * labeled alternative in {@link XPATHParser#f}.
	 * @param ctx the parse tree
	 */
	void exitFilterEq(XPATHParser.FilterEqContext ctx);
	/**
	 * Enter a parse tree produced by the {@code filterOR}
	 * labeled alternative in {@link XPATHParser#f}.
	 * @param ctx the parse tree
	 */
	void enterFilterOR(XPATHParser.FilterORContext ctx);
	/**
	 * Exit a parse tree produced by the {@code filterOR}
	 * labeled alternative in {@link XPATHParser#f}.
	 * @param ctx the parse tree
	 */
	void exitFilterOR(XPATHParser.FilterORContext ctx);
	/**
	 * Enter a parse tree produced by the {@code filterAND}
	 * labeled alternative in {@link XPATHParser#f}.
	 * @param ctx the parse tree
	 */
	void enterFilterAND(XPATHParser.FilterANDContext ctx);
	/**
	 * Exit a parse tree produced by the {@code filterAND}
	 * labeled alternative in {@link XPATHParser#f}.
	 * @param ctx the parse tree
	 */
	void exitFilterAND(XPATHParser.FilterANDContext ctx);
	/**
	 * Enter a parse tree produced by the {@code filterNOT}
	 * labeled alternative in {@link XPATHParser#f}.
	 * @param ctx the parse tree
	 */
	void enterFilterNOT(XPATHParser.FilterNOTContext ctx);
	/**
	 * Exit a parse tree produced by the {@code filterNOT}
	 * labeled alternative in {@link XPATHParser#f}.
	 * @param ctx the parse tree
	 */
	void exitFilterNOT(XPATHParser.FilterNOTContext ctx);
	/**
	 * Enter a parse tree produced by {@link XPATHParser#tagName}.
	 * @param ctx the parse tree
	 */
	void enterTagName(XPATHParser.TagNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link XPATHParser#tagName}.
	 * @param ctx the parse tree
	 */
	void exitTagName(XPATHParser.TagNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link XPATHParser#attName}.
	 * @param ctx the parse tree
	 */
	void enterAttName(XPATHParser.AttNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link XPATHParser#attName}.
	 * @param ctx the parse tree
	 */
	void exitAttName(XPATHParser.AttNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link XPATHParser#filename}.
	 * @param ctx the parse tree
	 */
	void enterFilename(XPATHParser.FilenameContext ctx);
	/**
	 * Exit a parse tree produced by {@link XPATHParser#filename}.
	 * @param ctx the parse tree
	 */
	void exitFilename(XPATHParser.FilenameContext ctx);
}