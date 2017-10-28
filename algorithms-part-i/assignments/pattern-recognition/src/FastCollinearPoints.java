/**
 * Created by yuanruoliang on 6/8/17.
 */
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;

import java.util.Arrays;
import java.util.LinkedList;

public class FastCollinearPoints {

    private final LineSegment[] lineSegments;

    /**
     * Finds all line segments containing 4 points or more points.
     */
    public FastCollinearPoints(Point[] points) {

        checkNulls(points);
        Point[] sortedPoints = points.clone();
        Arrays.sort(sortedPoints);

        //Single pass to check duplicates in sorted array
        checkDuplicates(sortedPoints);

        final int N = points.length;
        final LinkedList<LineSegment> linkedList = new LinkedList<>();

        //For every point
        for ( int i = 0; i < N; i++ ) {

            Point p = sortedPoints[i]; // Reference point
            Point[] pointsBySlope = sortedPoints.clone();
            Arrays.sort( pointsBySlope, p.slopeOrder() );

            //Find a single maximal line segment
            int j = 1; //skip reference point at x == 0
            while ( j < N ) {

                LinkedList<Point> candidatePoints = new LinkedList<>();
                final double SLOPE = p.slopeTo( pointsBySlope[j] );

                do { //Keep adding consecutive points if slope is within reference
                    candidatePoints.add( pointsBySlope[j++] );
                } while (j < N && p.slopeTo( pointsBySlope[j] ) == SLOPE);

                // Max line segment if
                // 1 - At least 3 collinear points excluding p (total of 4)
                // 2 - "p" must be the smallest point compared to all other candidate points.
                // The max line segment is created by the point "p" and the maximal (last) point in candidatePoints

                if ( candidatePoints.size() >= 3 && p.compareTo( candidatePoints.peek() ) < 0 )
                        linkedList.add( new LineSegment(p, candidatePoints.removeLast()) );

            }
        }

        //Add all line segments to result
        lineSegments = linkedList.toArray(new LineSegment[0]);
    }

    private void checkDuplicates(Point[] sortedPoints){
        for (int i = 0; i < sortedPoints.length - 1; i++)
            if( sortedPoints[i].compareTo(sortedPoints[i+1]) == 0)
                throw new java.lang.IllegalArgumentException("Duplicate Point in input array.");
    }

    private void checkNulls(Point[] points){
        if ( points == null ) throw new java.lang.IllegalArgumentException("Null argument.");

        for ( Point p : points )
            if(p == null) throw new java.lang.IllegalArgumentException("Null Point in input array.");

        return;
    }

    public int numberOfSegments() { return lineSegments.length; }

    public LineSegment[] segments() { return lineSegments.clone(); }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}