// Generated from /Users/mac126/19fall/udemy/spring/cse232bProject/src/XPathParser/XPATH.g4 by ANTLR 4.7.2
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link XPATHParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface XPATHVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by the {@code apSingle}
	 * labeled alternative in {@link XPATHParser#ap}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitApSingle(XPATHParser.ApSingleContext ctx);
	/**
	 * Visit a parse tree produced by the {@code apDouble}
	 * labeled alternative in {@link XPATHParser#ap}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitApDouble(XPATHParser.ApDoubleContext ctx);
	/**
	 * Visit a parse tree produced by {@link XPATHParser#doc}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDoc(XPATHParser.DocContext ctx);
	/**
	 * Visit a parse tree produced by the {@code rpSelf}
	 * labeled alternative in {@link XPATHParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRpSelf(XPATHParser.RpSelfContext ctx);
	/**
	 * Visit a parse tree produced by the {@code rpText}
	 * labeled alternative in {@link XPATHParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRpText(XPATHParser.RpTextContext ctx);
	/**
	 * Visit a parse tree produced by the {@code rpSingleSlash}
	 * labeled alternative in {@link XPATHParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRpSingleSlash(XPATHParser.RpSingleSlashContext ctx);
	/**
	 * Visit a parse tree produced by the {@code rpChildren}
	 * labeled alternative in {@link XPATHParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRpChildren(XPATHParser.RpChildrenContext ctx);
	/**
	 * Visit a parse tree produced by the {@code rpAttri}
	 * labeled alternative in {@link XPATHParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRpAttri(XPATHParser.RpAttriContext ctx);
	/**
	 * Visit a parse tree produced by the {@code rpTag}
	 * labeled alternative in {@link XPATHParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRpTag(XPATHParser.RpTagContext ctx);
	/**
	 * Visit a parse tree produced by the {@code rpParent}
	 * labeled alternative in {@link XPATHParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRpParent(XPATHParser.RpParentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code rpParentheses}
	 * labeled alternative in {@link XPATHParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRpParentheses(XPATHParser.RpParenthesesContext ctx);
	/**
	 * Visit a parse tree produced by the {@code rpCondition}
	 * labeled alternative in {@link XPATHParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRpCondition(XPATHParser.RpConditionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code rpComma}
	 * labeled alternative in {@link XPATHParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRpComma(XPATHParser.RpCommaContext ctx);
	/**
	 * Visit a parse tree produced by the {@code rpDoubleSlash}
	 * labeled alternative in {@link XPATHParser#rp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRpDoubleSlash(XPATHParser.RpDoubleSlashContext ctx);
	/**
	 * Visit a parse tree produced by the {@code filterIs}
	 * labeled alternative in {@link XPATHParser#f}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFilterIs(XPATHParser.FilterIsContext ctx);
	/**
	 * Visit a parse tree produced by the {@code filterRp}
	 * labeled alternative in {@link XPATHParser#f}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFilterRp(XPATHParser.FilterRpContext ctx);
	/**
	 * Visit a parse tree produced by the {@code filterParentheses}
	 * labeled alternative in {@link XPATHParser#f}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFilterParentheses(XPATHParser.FilterParenthesesContext ctx);
	/**
	 * Visit a parse tree produced by the {@code filterEq}
	 * labeled alternative in {@link XPATHParser#f}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFilterEq(XPATHParser.FilterEqContext ctx);
	/**
	 * Visit a parse tree produced by the {@code filterOR}
	 * labeled alternative in {@link XPATHParser#f}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFilterOR(XPATHParser.FilterORContext ctx);
	/**
	 * Visit a parse tree produced by the {@code filterAND}
	 * labeled alternative in {@link XPATHParser#f}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFilterAND(XPATHParser.FilterANDContext ctx);
	/**
	 * Visit a parse tree produced by the {@code filterNOT}
	 * labeled alternative in {@link XPATHParser#f}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFilterNOT(XPATHParser.FilterNOTContext ctx);
	/**
	 * Visit a parse tree produced by {@link XPATHParser#tagName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTagName(XPATHParser.TagNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link XPATHParser#attName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAttName(XPATHParser.AttNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link XPATHParser#filename}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFilename(XPATHParser.FilenameContext ctx);
}