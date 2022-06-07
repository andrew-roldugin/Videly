import 'package:client/domain/models/entity/video_model.dart';
import 'package:client/widgets/video/comment_list_widget.dart';
import 'package:client/widgets/video/video.dart';
import 'package:client/widgets/video/video_description_widget.dart';
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
    return SafeArea(
      child: Scaffold(
        body: Column(
          children:  [
            const VideoWidget(),
            const VideoDescriptionWidget(video: null),
            const Padding(
              padding: EdgeInsets.symmetric(vertical: 10),
              child: Text("Комментарии"),
            ),
            Expanded(
              child: CommentListWidget.create("POmq6G4M39ed1ffAjw1K"),
            )
          ],
        ),
      ),
    );
  }
}
