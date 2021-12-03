package store;

/**
 * @auther wy
 * @create 2021/10/30 9:43
 */
public abstract class UpdateItem {
    public abstract int updateVaule(int oldValue, int oldSellIn);

    public abstract int updateSellIn(int oldSellIn);

    public void updata(Item item){

            int newValue = updateVaule(item.getValue(), item.getSellIn());
            int newSellIn = updateSellIn(item.getSellIn());
            if(newValue > 50){
                newValue = 50;
            }
            if(newValue < 0){
                newValue = 0;
            }
            item.setValue(newValue);
            item.setSellIn(newSellIn);

    }
}
