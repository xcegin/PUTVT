package main.graph.renderers;

import main.graph.helper.IntersectionUtil;
import main.graph.helper.RenderingUtil;
import prefuse.Constants;
import prefuse.render.EdgeRenderer;
import prefuse.visual.EdgeItem;
import prefuse.visual.VisualItem;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.List;

import static main.graph.constants.VisualizationParameters.EDGE_THICKNESS;
import static main.graph.constants.VisualizationParameters.NODE_SIZE;
/**
 * Taken from https://plugins.jetbrains.com/plugin/8087-graph-database-support source code
 */
public class CustomEdgeRenderer extends EdgeRenderer {

    private static final double RADIUS = (NODE_SIZE + EDGE_THICKNESS) / 2;

    public CustomEdgeRenderer(int edgeTypeLine) {
        super(edgeTypeLine);
    }

    @Override
    protected Shape getRawShape(VisualItem item) {
        EdgeItem edge = (EdgeItem) item;
        VisualItem item1 = edge.getSourceItem();
        VisualItem item2 = edge.getTargetItem();

        getAlignedPoint(m_tmpPoints[0], item1.getBounds(), m_xAlign1, m_yAlign1);
        getAlignedPoint(m_tmpPoints[1], item2.getBounds(), m_xAlign2, m_yAlign2);
        m_curWidth = (float) (m_width * getLineWidth(item));

        double angle = 0.261799 * 3;

        boolean isLoopNode = item1 == item2;
        if (!isLoopNode && edge.isDirected() && m_edgeArrow != Constants.EDGE_ARROW_NONE) {
            boolean forward = (m_edgeArrow == Constants.EDGE_ARROW_FORWARD);
            Point2D start = m_tmpPoints[forward ? 0 : 1];
            Point2D end = m_tmpPoints[forward ? 1 : 0];

            VisualItem dest = forward ? edge.getTargetItem() : edge.getSourceItem();
            Point2D center = new Point2D.Double(dest.getBounds().getCenterX(), dest.getBounds().getCenterY());
            List<Point2D> intersections = IntersectionUtil.getCircleLineIntersectionPoint(start, end, center, dest.getBounds().getWidth() / 2);

            if (intersections.size() == 0) {
                try {
                    throw new Exception("What have I become my sweetest friend, everyone I know goes away in the end.",null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            end = intersections.get(0);

            AffineTransform at = getArrowTrans(start, end, m_curWidth);
            m_curArrow = at.createTransformedShape(m_arrowHead);

            Point2D lineEnd = m_tmpPoints[forward ? 1 : 0];
            lineEnd.setLocation(0, -m_arrowHeight);
            at.transform(lineEnd, lineEnd);
        } else if (isLoopNode && edge.isDirected() && m_edgeArrow != Constants.EDGE_ARROW_NONE) {
            double x = m_tmpPoints[0].getX();
            double y = m_tmpPoints[0].getY();

            Point.Double[] arrowPoints = RenderingUtil.arrow(angle, RADIUS, x, y, m_arrowHeight + 8);
            AffineTransform at = getArrowTrans(arrowPoints[0], arrowPoints[1], m_curWidth);
            m_curArrow = at.createTransformedShape(m_arrowHead);
        } else {
            m_curArrow = null;
        }

        Shape shape;
        double n1x = m_tmpPoints[0].getX();
        double n1y = m_tmpPoints[0].getY();
        double n2x = m_tmpPoints[1].getX();
        double n2y = m_tmpPoints[1].getY();
        if (isLoopNode) {
            shape = RenderingUtil.loopArrow(angle, RADIUS, n1x, n1y, m_arrowHeight);
        } else {
            m_line.setLine(n1x, n1y, n2x, n2y);
            shape = m_line;
        }

        return shape;
    }

    @Override
    public boolean locatePoint(Point2D p, VisualItem item) {
        Shape s = getShape(item);
        if (s == null) {
            return false;
        } else {
            double width = item.getSize() * EDGE_THICKNESS;
            double halfWidth = width / 2.0;
            return s.intersects(p.getX() - halfWidth, p.getY() - halfWidth, width, width)
                    || (m_curArrow != null && m_curArrow.contains(p.getX(), p.getY()));
        }
    }
}
