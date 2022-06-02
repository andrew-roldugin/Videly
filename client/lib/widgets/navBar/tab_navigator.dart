import 'package:client/domain/models/tab.dart';
import 'package:client/pages/account_page.dart';
import 'package:client/pages/history_page.dart';
import 'package:client/pages/home_page.dart';
import 'package:client/pages/subscriptions_page.dart';
import 'package:flutter/material.dart';

class TabNavigator extends StatelessWidget {
  // TabNavigator принимает:
  // navigatorKey - уникальный ключ для NavigatorState
  // tabItem - текущий пункт меню
  const TabNavigator({required this.navigatorKey, required this.tabItem});

  final GlobalKey<NavigatorState> navigatorKey;
  final TabItem tabItem;

  @override
  Widget build(BuildContext context) {
    return Navigator(
      key: navigatorKey,
      onGenerateRoute: (routeSettings) {
        Widget currentPage = ErrorWidget(Exception("Произошла ошибка"));
        if (tabItem == TabItem.HOME) {
          currentPage = const HomePage();
        } else if (tabItem == TabItem.SUBSCRIPTIONS) {
          currentPage = const SubscriptionsPage();
        } else if (tabItem == TabItem.ACCOUNT) {
          currentPage = const AccountPage();
        } else if (tabItem == TabItem.HISTORY) {
          currentPage = const HistoryPage();
        }
        return MaterialPageRoute(builder: (context) => currentPage,);
      },
    );
  }
}
