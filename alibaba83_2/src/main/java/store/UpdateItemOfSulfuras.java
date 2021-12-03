package store;

/**
 * @auther wy
 * @create 2021/10/30 9:46
 */
public class UpdateItemOfSulfuras extends UpdateItem{
    @Override
    public int updateVaule(int oldValue, int oldSellIn) {
        return oldValue;
    }

    @Override
    public int updateSellIn(int oldSellIn) {
        return oldSellIn;
    }

}
