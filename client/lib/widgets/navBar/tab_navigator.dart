import 'package:client/domain/models/tab.dart';
import 'package:client/pages/account_page.dart';
import 'package:client/pages/history_page.dart';
import 'package:client/pages/home_page.dart';
import 'package:client/pages/subscriptions_page.dart';
import 'package:flutter/foundation.dart';
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
      //key: navigatorKey,
      initialRoute: "tab:$tabItem",
      onGenerateRoute: (routeSettings) {
        Widget currentPage;
        if (routeSettings.name!.contains("tab:")) {
          currentPage = Center(child: Text(routeSettings.name!));
          return MaterialPageRoute(
            builder: (context) => currentPage,
          );
        }
        // Widget currentPage =
        //     routeSettings.
        //     name != null && routeSettings.name!.isNotEmpty
        //         ? Text(routeSettings.name!)
        //         : Container();
        // var w = navigatorKey.currentWidget;

        switch (tabItem) {
          case TabItem.HOME:
            {
              currentPage = const HomePage();
              break;
            }
          case TabItem.SUBSCRIPTIONS:
            {
              currentPage = const SubscriptionsPage();
              break;
            }
          case TabItem.ACCOUNT:
            {
              currentPage = const AccountPage();
              break;
            }
          case TabItem.HISTORY:
            {
              currentPage = const HistoryPage();
              break;
            }
        }
        return MaterialPageRoute(
          builder: (context) => currentPage,
        );
      },
    );
  }
}
