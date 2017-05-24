package com.example.myshop.Utils;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;

import com.example.myshop.bean.ShoppingCart;
import com.example.myshop.bean.Wares;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 刘博良 on 2017/5/7.
 */

public class cartProvider {
    private static final String CART_JSON ="cart_json";
    private SparseArray<ShoppingCart> datas =null;

    private Context mContext;

    public cartProvider(Context mContext) {
        this.mContext = mContext;
        datas =new SparseArray<>();

        listToSparse();
    }

    public void put(ShoppingCart cart){
        ShoppingCart shopCart =datas.get(cart.getId());
        if (shopCart !=null)
            shopCart.setmCount(shopCart.getmCount()+1);
        else
            datas.put(cart.getId(),cart);

        commit();

    }
    public void put (Wares wares){
        put(convertWares(wares));
    }

    public void update(ShoppingCart cart){
        datas.put(cart.getId(),cart);
        commit();

    }

    public void delete(ShoppingCart cart){

        datas.delete(cart.getId());
        commit();
    }

    public List<ShoppingCart> getAll(){
        return getDataFromLocal();
    }

    private void commit(){
        List<ShoppingCart> carts =sparseToList();

        PerferenceUtils.putString(mContext,CART_JSON,JsonUtil.toJson(carts));
    }

    private List<ShoppingCart> sparseToList(){
        int size =datas.size();
        List<ShoppingCart> list =new ArrayList<>(size);
        for (int i = 0; i <size ; i++) {
            list.add(datas.valueAt(i));
        }
        return list;
    }

    private void listToSparse(){

        List<ShoppingCart> list =getDataFromLocal();
        if (list !=null &&list.size()>0){
            for (ShoppingCart cart:list) {
                datas.put(cart.getId(),cart);
            }
        }
    }

    private List<ShoppingCart> getDataFromLocal(){
        String json =PerferenceUtils.getString(mContext,CART_JSON);
        List<ShoppingCart> list =null;
        if (json !=null){
            list =JsonUtil.fromeJson(json, new TypeToken<List<ShoppingCart>>(){}.getType());
        }

        Log.e("list null is",String.valueOf(list==null));
        return list;
    }
    private ShoppingCart convertWares(Wares wares){
        ShoppingCart shoppingCart =new ShoppingCart();
        shoppingCart.setCheack(true);
        shoppingCart.setId(wares.getId());
        shoppingCart.setName(wares.getName());
        shoppingCart.setPrice(wares.getPrice());
        shoppingCart.setImgUrl(wares.getImgUrl());
        shoppingCart.setSale(wares.getSale());
        return shoppingCart;
    }
}
