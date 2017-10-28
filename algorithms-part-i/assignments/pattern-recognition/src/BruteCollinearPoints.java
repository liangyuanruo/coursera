/**
 * Created by yuanruoliang on 6/8/17.
 */

import java.util.Arrays;
import java.util.LinkedList;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;


public class BruteCollinearPoints {

    private LineSegment[] lineSegments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points){

        checkNulls(points);
        Point[] sortedPoints = points.clone();
        // Sort by minimum point
        Arrays.sort(sortedPoints);

        //Single pass to check duplicates in sorted array
        checkDuplicates(sortedPoints);

        LinkedList<LineSegment> linkedList = new LinkedList<>();

        final int N = points.length;

        for (int a = 0; a < N - 3; a++) {
            Point ptA = sortedPoints[a];

            for (int b = a + 1; b < N - 2; b++) {
                Point ptB = sortedPoints[b];
                double slopeAB = ptA.slopeTo(ptB);

                for (int c = b + 1; c < N - 1; c++) {
                    Point ptC = sortedPoints[c];
                    double slopeAC = ptA.slopeTo(ptC);
                    if (slopeAB == slopeAC) {

                        for (int d = c + 1; d < N; d++) {
                            Point ptD = sortedPoints[d];
                            double slopeAD = ptA.slopeTo(ptD);
                            if (slopeAB == slopeAD) {
                                linkedList.add(new LineSegment(ptA, ptD));
                            }
                        }
                    }
                }
            }
        }
        lineSegments = linkedList.toArray( new LineSegment[linkedList.size()] );

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

    // the number of line segments
    public int numberOfSegments(){ return lineSegments.length; }

    // the line segments
    public LineSegment[] segments(){ return lineSegments.clone(); }

    public static void main ( String[] args ){

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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();

        StdOut.println(collinear.numberOfSegments());
    }
}