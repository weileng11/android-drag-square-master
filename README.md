<h1>Modified by Swifty</h1>
refactor the repository, easy to use with gradle import.
[![](https://jitpack.io/v/SwiftyWang/android-drag-square.svg)](https://jitpack.io/#SwiftyWang/android-drag-square)

<h3>How to use</h3>
Add it in your root build.gradle at the end of repositories:

```gradle
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

Step 2. Add the dependency
```gradle
	dependencies {
	        compile 'com.github.SwiftyWang.android-drag-square:dragsquareimage:1.2.3'
	}
```

<h3>get DraggablePresenter</h3>
```java
        DraggableSquareView dragSquare = (DraggableSquareView) findViewById(R.id.drag_square);
        contentText = (TextView) findViewById(R.id.contentText);
        draggablePresent = new DraggablePresentImpl(fragment, dragSquare);
        draggablePresent = new DraggablePresentImpl(activity, dragSquare);
```

need pass activity callback to DraggablePresentImpl:
```java
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {
        draggablePresent.onActivityResult(requestCode, resultCode, result);
    }
```

<h3>Set customer dialog</h3>
Customer dialog must extends ActionDialog.class
```java
    draggablePresent.setCustomActionDialog(new MyActionDialog(Context));
```

<h3>listen image changes</h3>
```java
    dragSquare.setImageChangesListener(imageChangesListener);

    public interface ImageChangesListener {
        void onImageAdded(String uri, int index);

        void onImageEdited(String uri, int index);

        void onImageDeleted(String uri, int index);
    }
```

<h3>All public apis</h3>
```java
    SparseArray<String> getImageUrls();

    void setImages(String... imageUrls);

    void setCustomActionDialog(ActionDialog actionDialog);
```


# android-drag-square
edit personal data which enables users to drag and rank image order

编辑个人资料，图片可拖拽排序。有点像可拖拽的gridView，但是会更流畅。<br>
这个demo是探探的个人资料编辑页面，受网上一位朋友的委托，该库模仿了其拖动效果。<br>
探探的安卓工程师，应该特别牛逼吧。因为最初时，这种拖拽效果真的无从下手。反编译探探的源代码，发现它做了很严肃的混淆处理。然后用Hierarchy Viewer看了View的层级，这才有了一点点的思路。<br>
在代码撰写的过程中，我也踩了不少坑。细看代码深处，或许你会有一丝丝的收获吧。<br>
当然，在最初的最初，我搜了不少的draggable gridview的仓库，可惜用起来的时候发现不够流畅、不够灵活。

### 截图
<td>
	 <img src="capture1.gif" width="290" height="485" />
	 <img src="capture2.gif" width="290" height="485" />
	 <img src="capture3.gif" width="290" height="485" />
</td>
