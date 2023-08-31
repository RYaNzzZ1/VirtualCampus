 
package seu.list.common;

import java.io.Serializable;
/**
 * 类{@code Goods}为商品类
 * @author 欧阳瑜
 * @version 1.0
 * 
 */
public class Goods implements Serializable{
		private static final long serialVersionUID = 1L;
	
		private int goodsID;
		private String goodsName;
		private double goodsPrice;
		private int goodsNumber;

		public Goods() {
			super();
		}
		/**
		 * 构造函数
		 * @param goodsid 商品编号
		 * @param goodsname 商品名
		 * @param goodsprice 商品价格
		 * @param goodsnumber 库存
		 */
		public Goods(int goodsid,String goodsname,double goodsprice,int goodsnumber){
			goodsID =goodsid;
			goodsName =goodsname;
			goodsPrice =goodsprice;
			goodsNumber =goodsnumber;
		}

		public int getGoodsid() {
			return goodsID;
		}

		public void setGoodsid(int goodsid) {
			goodsID = goodsid;
		}
		public String getGoodsname() {
			return goodsName;
		}

		public void setGoodsname(String goodsname) {
			goodsName = goodsname;
		}

		public double getGoodsprice() {
			return goodsPrice;
		}

		public void setGoodsprice(double goodsprice) {
			goodsPrice = goodsprice;
		}

		public int getGoodsnumber() {
			return goodsNumber;
		}

		public void setGoodsnumber(int goodsnumber) {
			goodsNumber = goodsnumber;
		}

		@Override
		public String toString() {
			return "Goods{" + "GoodsID" + goodsID + ", GoodsName" + goodsName + ", GoodsPrice" + goodsPrice + ", GoodsNumber" + goodsNumber + "}";
		}
}
