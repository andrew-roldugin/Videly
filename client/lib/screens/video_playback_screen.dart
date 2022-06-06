import 'package:client/widgets/video/comment_list_widget.dart';
import 'package:client/widgets/video/video.dart';
import 'package:client/widgets/video/video_description_widget.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';

class VideoPlaybackScreen extends StatefulWidget {
  static const routeName = "/main/video";

  const VideoPlaybackScreen({Key? key}) : super(key: key);

  @override
  State<VideoPlaybackScreen> createState() => _VideoPlaybackScreenState();
}

class _VideoPlaybackScreenState extends State<VideoPlaybackScreen> {
  @override
  Widget build(BuildContext context) {
    if (kDebugMode) {
      print("_VideoPlaybackScreenState");
    }

    return SafeArea(
      child: Scaffold(
        body: Column(
          children: const [
            VideoWidget(),
            VideoDescriptionWidget(),
            Padding(
              padding: EdgeInsets.symmetric(vertical: 10),
              child: Text("Комментарии"),
            ),
            Expanded(
              child: CommentListWidget(),
            )
          ],
        ),
      ),
    );
  }
}
