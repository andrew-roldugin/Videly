import 'package:client/screens/main_screen.dart';
import 'package:client/screens/signin_screen.dart';
import 'package:client/screens/video_playback_screen.dart';
import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';

class HomePage extends StatefulWidget {
  const HomePage({Key? key}) : super(key: key);

  @override
  State<HomePage> createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  void _goToVideoScreen(BuildContext context) {
    Navigator.pushNamed(context, VideoPlaybackScreen.routeName);
  }

  @override
  Widget build(BuildContext context) {
    return SafeArea(
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          const Text("Главная"),
          MaterialButton(
            child: Text("На стр. видео"),
            onPressed: () => _goToVideoScreen(context),
          )
        ],
      ),
    );
  }
}
