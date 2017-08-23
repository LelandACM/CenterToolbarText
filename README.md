- **使用SearchView过程中你需要注意的问题和一个常见的BackPress退出问题**
1. 开启系统搜索服务
```	
SearchManager manager = (SearchManager) getContext().getSystemService(Context.SEARCH_SERVICE);
```
2. 实例化SearchView对象
```
mSearchView = (SearchView) item.getActionView();
```
3. 设置`setOnQueryTextListener`事件监听搜索条件
```
//on query text listener
private SearchView.OnQueryTextListener onQueryTextListener = new SearchView.OnQueryTextListener() {
    @Override //submit the search condition
    public boolean onQueryTextSubmit(String query) {
        Toast.makeText(getContext(), "Search Data in fragment", Toast.LENGTH_SHORT).show();
        return false;
    }
    @Override //the search condition once changed will invoke this funtion
    public boolean onQueryTextChange(String newText) {
        return false;
    }
};
```
4. 设置`setOnQueryTextFocusChangeListener`事件监听输入框焦点的变化通过此函数解决了`OnBackPressed`直接退出APP的问题
```
//the focus of the query condition box once changed , will call this funtion
mSearchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
	@Override
    public void onFocusChange(View view, boolean focused) {
        //charge the focus is changed and the searchview icon isn't iconified
        if (!focused && !mSearchView.isIconified()) {
            //in the fragment you mus you this way can solve it
            MenuItemCompat.collapseActionView(item);
        }
    }
});
```
5. 网上说的关闭活动的SearchView时的解决办法是`override` `onBackPressed`函数
```
@Override
public void onBackPressed() {
    if (!searchView.isIconified()) {
        searchView.setIconified(true);
    } else {
        super.onBackPressed();
    }
}
```
在Fragment中当然没有这个函数可以让你重写，但是可以通过在基类implement OnBackPressed*`(自己定义的)`*接口详情请见[onBackPressed() for Fragments][8],我通过此方法，使用上面的这种发现效果图如`before.gif`并不是解决办法，还是监听`setOnQueryTextFocusChangeListener`是正确的，正确图片如下`perfect.gif`

![before.gif](http://upload-images.jianshu.io/upload_images/4073499-f8eedbd1047b9f6c.gif?imageMogr2/auto-orient/strip)
![perfect.gif](http://upload-images.jianshu.io/upload_images/4073499-46e7c5831c8dd0b1.gif?imageMogr2/auto-orient/strip)
- **参考链接**

[如何使用菜单`Menu`][1]

[如何使用`SearchView`][2]

[Android系统自带样式][7]

[Implementing SearchView in action bar][3]

[Android toolbar center title and custom font][4]

[How do I close a SearchView programmatically?][5]

[onPrepareOptionsMenu not getting called in Fragments][6]
- **简书博客传送门**

CenterTooolBarText:<https://github.com/GitACDreamer/CenterToolbarText>

##### 希望大家多多关注star and fork，各位觉得如果有帮助给个赞，谢谢
[1]:https://developer.android.com/guide/topics/ui/menus.html
[2]:https://developer.android.com/reference/android/support/v7/widget/SearchView.html
[3]:https://stackoverflow.com/questions/21585326/implementing-searchview-in-action-bar
[4]:https://stackoverflow.com/questions/26533510/android-toolbar-center-title-and-custom-font
[5]:https://stackoverflow.com/questions/17506230/how-do-i-close-a-searchview-programmatically
[6]:https://stackoverflow.com/questions/15656953/onprepareoptionsmenu-not-getting-called-in-fragments

[7]:http://blog.csdn.net/shakespeare001/article/details/7779011

[8]:https://medium.com/@Wingnut/onbackpressed-for-fragments-357b2bf1ce8e