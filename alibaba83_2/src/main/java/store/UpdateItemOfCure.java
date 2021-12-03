package store;

/**
 * @auther wy
 * @create 2021/10/30 9:47
 */
public class UpdateItemOfCure extends UpdateItem{
    @Override
    public int updateVaule(int oldValue, int oldSellIn) {
        return Math.max(0, oldValue - 2);
    }

    @Override
    public int updateSellIn(int oldSellIn) {
        return oldSellIn - 1;
    }


}
