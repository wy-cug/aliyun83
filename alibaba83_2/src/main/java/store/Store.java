package store;

// Please don't modify the class name.
public class Store {
    Item[] items;

    // Please don't modify the signature of this method.
    public Store(Item[] items) {
        this.items = items;
    }

    // Please don't modify the signature of this method.
    public void updateValue() {
        for (int i = 0; i < items.length; i++) {
            /**
             * 商品有多种可能
             *   普通商品
             *   Aged Wine酒
             *   Sulfuras 魔法锤
             *   Show Ticket 演出票
             *   新增：Cure 特效药
             */

            UpdateItem updateItem;
            switch (items[i].getName()){
                case "Aged Wine":
                    updateItem = new UpdateItemOfAgedWine();
                    break;
                case "Sulfuras":
                    updateItem = new UpdateItemOfSulfuras();
                    break;
                case "Show Ticket":
                    updateItem = new UpdateItemOfShowTicket();
                    break;
                case "Cure":
                    updateItem = new UpdateItemOfCure();
                    break;
                default:
                    updateItem = new UpdateItemGenral();
            }
            updateItem.updata(items[i]);
        }
    }


}