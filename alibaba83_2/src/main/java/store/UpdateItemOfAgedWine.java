package store;

/**
 * @auther wy
 * @create 2021/10/30 9:45
 */
public class UpdateItemOfAgedWine extends UpdateItem{
    @Override
    public int updateVaule(int oldValue, int oldSellIn) {
        int newVaule;
        if(oldSellIn <= 0){
            newVaule = oldValue + 2;
        }else{
            newVaule = oldValue + 1;
        }
        return Math.min(50, newVaule);
    }

    @Override
    public int updateSellIn(int oldSellIn) {
        return oldSellIn - 1;
    }

}
