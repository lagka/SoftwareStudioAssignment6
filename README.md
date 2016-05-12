# Software Studio Assignment 6

## Notes
+ You will have to import the libraries on your own. All the libraries are provided on iLMS.
+ Even you followed the design TA shown in the video, you still have to explain the control of the program in the next section.

## Explanation of the Design
Character.java:
存放所有node
其初始值
以及有哪些聯結
以及負責繪畫聯結
(每次有新的圈圈進來後，就從他所有有聯結的判斷)是否在圓圈上
有就畫線

MainApplet.java:
loaddata讀輸入
mousedragged控制鼠標，取得鼠標位置
mousepress判斷是否按下，控制鼠標案下的變數值
button_add加上add的按鈕
button_clear加上清空按鈕
draw繪畫，若發現鼠標位置不在圓圈上，則幫他找個點繪上去(找角度)
keypress控制star war的要選擇哪個輸入





Example:
### Operation
+ Clicking on the button "Add All": users can add all the characters into network to be analyzed.
+ Clicking on the button "Clear": users can remove all the characters from network.
+ Hovering on the character: the name of the character will be revealed.
+ By dragging the little circle and drop it in the big circle, the character will be added to the network.
+ By pressing key 1~7 on the keyboard, users can switch between episodes.
+ ...etc.

### Visualization
+ The width of each link is visualized based on the value of the link.
+ ...etc.
