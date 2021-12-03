package store;

/**
 * @auther wy
 * @create 2021/10/30 9:46
 */
public class UpdateItemOfShowTicket extends UpdateItem{
    @Override
    public int updateVaule(int oldValue, int oldSellIn) {
        int newValue;
        if(oldSellIn<=0){
            newValue = 0;
        }
        else if(oldSellIn<=5){
            newValue = oldValue + 3;
        }
        else if(oldSellIn<=10){
            newValue = oldValue + 2;
        }
        else {
            newValue = oldValue + 1;
        }
        return Math.min(50, newValue);
    }

    @Override
    public int updateSellIn(int oldSellIn) {
        return oldSellIn - 1;
    }


}
