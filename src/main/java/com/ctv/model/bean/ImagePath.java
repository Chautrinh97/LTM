package com.ctv.model.bean;

public class ImagePath {
	private int productId;
	private String path;

	public ImagePath(){}
	public ImagePath(ImagePath imagePath){
		this.productId= imagePath.productId;
		this.path=imagePath.path;
	}
	public ImagePath(int productId, String path) {
		this.productId = productId;
		this.path = path;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
