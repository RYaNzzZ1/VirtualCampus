package seu.list.server.dao;

import seu.list.common.Goods;
import seu.list.common.Message;
import seu.list.common.MessageType;
import seu.list.server.db.Shop_DbAccess;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * 类{@code ShopSever}是商店和数据库进行连接的数据访问对象（data access object，DAO）
 */

public class ShopSever extends Shop_DbAccess {
    Connection con = null;
    Statement s = null;
    ResultSet rs = null;
    double money = 0;
    private Message mesFromClient;
    private Message mesToClient = new Message();

    public ShopSever(Message mesFromClient) {
        //System.out.println(mesFromClient);
        this.mesFromClient = mesFromClient;
    }

    /**
     * 方法{void excute()}实现网络通信
     */
    public void excute() {
        switch (this.mesFromClient.getMessageType()) {
            case MessageType.Goodsgetall: {
                this.mesToClient.setData(getList());
                break;
            }
            case MessageType.GoodsSearch_ID: {
                this.mesToClient.setData(SearchGoods_ID(Integer.parseInt((String) this.mesFromClient.getData())));
                break;
            }

            case MessageType.GoodsDelete: {
                String para = this.mesFromClient.getData().toString();
                int ID = Integer.parseInt(para);
                Deletegoods(ID);
                this.mesToClient.setData(1);
                break;
            }

            case MessageType.GoodsAdd: {
                Addgoods((Goods) this.mesFromClient.getData());
                this.mesToClient.setData(1);
                break;
            }

            case MessageType.ModifyGoods: {
                this.ModifyGoods((ArrayList<String>) this.mesFromClient.getData());
                break;
            }

            case MessageType.GoodsSearch_Name: {
                this.mesToClient.setData(this.SearchGoods_Name((String) this.mesFromClient.getData()));
                break;
            }

            case MessageType.Buy: {
                this.buy((ArrayList<String>) this.mesFromClient.getData());
                break;
            }
            case MessageType.Goodsgetturnover: {
                this.mesToClient.setData(GetTurnOver());
                break;
            }
            default:
                break;
        }
    }


    private ArrayList<Goods> GoodsList = new ArrayList<Goods>();

    public ArrayList<Goods> getGoodsList() {
        return GoodsList;
    }

    public void setGoodsList(ArrayList<Goods> goodsList) {
        GoodsList = goodsList;
    }

    /**
     * 方法{@code ArrayList<Goods> getList()}从数据库导出商品列表
     *
     * @return 所有商品的列表
     */
    public ArrayList<Goods> getList() {//从数据库导出
        try {
            con = getConnection();
            s = con.createStatement();// 创建SQL语句对象
            rs = s.executeQuery("select * from tb_Goods");    // 查询商品信息
            GoodsList.clear();

            while (rs.next()) {
                if (rs.getInt("GoodsID") != -1) {
                    Goods temp = new Goods();
                    temp.setGoodsid(rs.getInt("GoodsID"));
                    temp.setGoodsname(rs.getString("GoodsName"));
                    temp.setGoodsprice(rs.getDouble("GoodsPrice"));
                    temp.setGoodsnumber(rs.getInt("GoodsNumber"));
                    GoodsList.add(temp);
                } else
                    money = rs.getDouble("TurnOver");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection(con, rs, s);
            //		closeConnection(con, rs, s);
        }
        return GoodsList;
    }

    /**
     * 方法{@code  SearchGoods_Name(String temp)}对商品进行使用商品名的搜索
     *
     * @param goodName 输入的值
     * @return 符合搜索条件的商品的列表
     */
    public ArrayList<Goods> SearchGoods_Name(String goodName) {//按商品名称查找（所有符合）
        ArrayList<Goods> result = new ArrayList<Goods>();
        try {
            con = getConnection();
            s = con.createStatement();
            rs = s.executeQuery("select * from tb_Goods where GoodsName='" + goodName + "'");
            while (rs.next()) {
                Goods temp = new Goods();
                temp.setGoodsid(rs.getInt("GoodsID"));
                temp.setGoodsname(rs.getString("GoodsName"));
                temp.setGoodsprice(rs.getDouble("GoodsPrice"));
                temp.setGoodsnumber(rs.getInt("GoodsNumber"));
                result.add(temp);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection(con, rs, s);
        }
        return result;
        //        ArrayList<Goods> result = new ArrayList<Goods>();
//        GoodsList = getList();
//        for (int i = 0; i < GoodsList.size(); i++) {
//            Goods tempGoods = GoodsList.get(i);
//
//            if (temp.equals(tempGoods.getGoodsname()))
//                result.add(tempGoods);
//        }
//        return result;
    }

    /**
     * 方法{@code  SearchGoods_Name(String temp)}对商品进行使用商品ID的搜索
     *
     * @param temp 输入的值
     * @return 符合搜索条件的商品的列表
     */
    public ArrayList<Goods> SearchGoods_ID(int goodId) {//按商品ID查找（唯一）
        ArrayList<Goods> result = new ArrayList<Goods>();
        try {
            con = getConnection();
            s = con.createStatement();
            rs = s.executeQuery("select * from tb_Goods where GoodsID='" + goodId + "'");
            while (rs.next()) {
                Goods temp = new Goods();
                temp.setGoodsid(rs.getInt("GoodsID"));
                temp.setGoodsname(rs.getString("GoodsName"));
                temp.setGoodsprice(rs.getDouble("GoodsPrice"));
                temp.setGoodsnumber(rs.getInt("GoodsNumber"));
                result.add(temp);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection(con, rs, s);
        }
        return result;
    }

    /**
     * 方法{@code  buy(ArrayList<String> args)}//购买(学生)
     *
     * @param ArrayList<String> args arg[i]存id，arg[i+1]存购买的数量
     */
    public void buy(ArrayList<String> args) {//购买(学生)arg[i]存id，arg[i+1]存购买的数量
        try {
            int result = 0;
            for (int i = 0; i < args.size(); i += 2) {
                Goods temp = SearchGoods_ID(Integer.parseInt(args.get(i))).get(0);
                con = getConnection();
                s = con.createStatement();
                temp.setGoodsnumber(temp.getGoodsnumber() - Integer.parseInt(args.get(i + 1)));
                result = s.executeUpdate("update tb_Goods set GoodsNumber='" + temp.getGoodsnumber() + "'where GoodsID='" + temp.getGoodsid() + "'");
                money += temp.getGoodsprice() * Integer.parseInt(args.get(i + 1));
                closeConnection(con, rs, s);
            }
            con = getConnection();
            s = con.createStatement();
            result = s.executeUpdate("update tb_Goods set TurnOver='" + money + "'where GoodsID='" + (-1) + "'");//第一行放营收额
            closeConnection(con, rs, s);
            System.out.println(money);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 方法{@code void Addgoods(Goods temp)} 增加商品(管理员)
     *
     * @param temp 要增加的商品
     */
    public void Addgoods(Goods temp) {//增加商品(管理员)
        try {
            con = getConnection();
            s = con.createStatement();
            int result = 0;
            result = s.executeUpdate("insert into tb_Goods(GoodsID,GoodsName,GoodsPrice,GoodsNumber,TurnOver) values('" + temp.getGoodsid() + "','" + temp.getGoodsname() + "','" + temp.getGoodsprice() +
                    "','" + temp.getGoodsnumber() + "','" + 0 + "')");
			/*result=s.executeUpdate("insert into tb_Goods(GoodsID,GoodsName,GoodsPrice,GoodsNumber,TurnOver) values('"+temp.getGoodsid()+"','"+temp.getGoodsname()+"','"+2.2321+
					"','"+1+"','"+0+"')");*/
            System.out.println(temp.getGoodsid());
            System.out.println(temp.getGoodsprice());
            System.out.println(temp.getGoodsnumber());
            //System.out.println(result);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection(con, rs, s);
        }
    }

    /**
     * 方法{@code void Deletegoods(int ID))} 删除商品(管理员)
     *
     * @param ID 要删除的商品的ID
     */
    public void Deletegoods(int ID) {//下架商品(管理员)
        try {

            int result = 0;
            ArrayList<Goods> goods = SearchGoods_ID(ID);
            con = getConnection();
            s = con.createStatement();
            if (!goods.isEmpty()) {
                Goods temp = goods.get(0);
                result = s.executeUpdate("delete from tb_Goods where GoodsID='" + ID + "'");
            }
            //System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection(con, rs, s);
        }
    }

    /**
     * 方法{@code void ModifyGoods(ArrayList<String> args)} 修改商品(管理员)修改商品单价或者库存
     *
     * @param ModifyGoods(ArrayList<String> args)修改后的商品信息
     */
    public void ModifyGoods(ArrayList<String> args) {
        // 修改商品信息
        try {

            int result = 0;
            for (int i = 0; i < args.size(); i += 3) {
                int ID = Integer.parseInt(args.get(i));
                double price = Double.parseDouble(args.get(i + 1));
                int Number = Integer.parseInt(args.get(i + 2));
                Goods temp = SearchGoods_ID(ID).get(0);
                con = getConnection();
                s = con.createStatement();
                temp.setGoodsprice(price);
                result = s.executeUpdate("update tb_Goods set GoodsPrice='" + price + "'where GoodsID='" + ID + "'") &
                        s.executeUpdate("update tb_Goods set GoodsNumber='" + Number + "'where GoodsID='" + ID + "'");
                closeConnection(con, rs, s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    /**
     * 方法{@code double GetTurnOver())} 获得营业额
     */
    public double GetTurnOver() {
        double turnOver =0.;
        try {
            con = getConnection();
            s = con.createStatement();
            rs = s.executeQuery("select * from tb_Goods where GoodsID=-1");
            //double res= s.exe;
            rs.next();
            turnOver = rs.getDouble("TurnOver");
            if (turnOver == 0.) {
                return 0.0;
            } else {
                return turnOver;
            }
            //result=s.executeUpdate("update tb_Goods set GoodsPrice='"+price+"'where GoodsID='"+ID+"'");
            //System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnection(con, rs, s);
        }
        return turnOver;
    }

    public Message getMesToClient() {
        return this.mesToClient;
    }
}
