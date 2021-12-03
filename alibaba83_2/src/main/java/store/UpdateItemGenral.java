package store;

/**
 * @auther wy
 * @create 2021/10/30 10:12
 */
public class UpdateItemGenral extends UpdateItem{
    @Override
    public int updateVaule(int oldValue, int oldSellIn) {
        int newValue;
        if(oldSellIn<=0){
            newValue = oldValue - 2;
        }else{
            newValue = oldValue - 1;
        }
        return Math.min(newValue, 0);
    }

    @Override
    public int updateSellIn(int oldSellIn) {
        return oldSellIn - 1;
    }
}
