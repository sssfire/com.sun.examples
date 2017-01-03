package com.sun.example.java.generictype;

public class Main {
	
	public static void main(String[] args) {
		Main app = new Main();
		
		Shape rectangleShape = new Shape("RectangleShape");
		Shape redCarShape = new CarShape("RedCarShape");
		Shape flyCarShape = new SuperCarShape("FlyCarShape");
		
		
		BuildShapeTool<Shape> retangleTool= app.new BuildShapeTool<Shape>(rectangleShape);
		app.buildShape(retangleTool);
		app.buildBasicShape(retangleTool);
		
		BuildShapeTool<Shape> carShapeTool= app.new BuildShapeTool<Shape>(redCarShape);
		app.buildShape(carShapeTool);
		app.buildBasicShape(carShapeTool);
		
		BuildShapeTool<Shape> flyCarShapeTool= app.new BuildShapeTool<Shape>(flyCarShape);
		app.buildShape(flyCarShapeTool);
		app.buildBasicShape(flyCarShapeTool);
		
	}

	public void buildShape(BuildShapeTool<? extends Shape> shapeTool){
		System.out.println(shapeTool.toString());
	}
	
	public void buildBasicShape(BuildShapeTool<? super CarShape> shape){
		System.out.println(shape.toString());
	}
	
	class BuildShapeTool<T>{

		private T shape;
		
		public BuildShapeTool(T shape) {
			this.shape = shape;
		}
		
		@Override
		public String toString() {
			return shape.toString();
		}
		
	}
}
