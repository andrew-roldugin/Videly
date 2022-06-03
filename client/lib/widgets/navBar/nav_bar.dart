import 'package:client/controllers/tab_controller.dart';
import 'package:client/domain/models/tab.dart';
import 'package:flutter/material.dart';

class MyBottomNavigationBar extends StatelessWidget {
  const MyBottomNavigationBar({required this.controller});

  final MyTabController controller;

  @override
  Widget build(BuildContext context) {
    return BottomNavigationBar(
        selectedFontSize: 13,
        unselectedItemColor: Colors.grey,
        type: BottomNavigationBarType.fixed,
        currentIndex: controller.currentTab.index,
        // пункты меню
        items: [
          controller.buildItem(TabItem.HOME),
          controller.buildItem(TabItem.SUBSCRIPTIONS),
          controller.buildItem(TabItem.HISTORY),
          controller.buildItem(TabItem.ACCOUNT),
        ],
        onTap: (index) => controller.selectTab(TabItem.values[index]));
  }
}
