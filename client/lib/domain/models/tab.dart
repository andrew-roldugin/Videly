import 'package:client/screens/main_screen.dart';
import 'package:flutter/material.dart';

abstract class Tab {
  final String name;
  final Color selectedItemColor = const Color.fromRGBO(65, 75, 178, 1);
  final Color unselectedItemColor = Colors.grey;
  final IconData icon;

  const Tab({required this.name, required this.icon});
}

class HomeTab extends Tab {
  const HomeTab() : super(name: 'Главная', icon: Icons.home);
}

class SubscriptionsTab extends Tab {
  const SubscriptionsTab() : super(name: 'Подписки', icon: Icons.favorite);
}

class HistoryTab extends Tab {
  const HistoryTab() : super(name: 'История', icon: Icons.video_collection);
}

class AccountTab extends Tab {
  const AccountTab() : super(name: 'Аккаунт', icon: Icons.account_circle);
}

enum TabItem { HOME, SUBSCRIPTIONS, HISTORY, ACCOUNT }
