public class Rectangulator{

public static void main (String[] arg)
{
 int length	= Integer.parseInt (arg[0]);
 int width	= Integer.parseInt (arg[1]);

 Rectangle myRectangle = new Rectangle(length, width);
 String output = String.format("***Your rectangle ***\n\nLength: %d\nWidth: %d\nArea: %d\nPerimeter: %d\n\n", myRectangle.length, myRectangle.width, myRectangle.getArea(), myRectangle.getPerimeter());
 System.out.println(output);
}

}
