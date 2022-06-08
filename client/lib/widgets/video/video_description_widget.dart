import 'dart:convert';

import 'package:client/domain/models/entity/video_model.dart';
import 'package:client/screens/channel_screen.dart';
import 'package:client/screens/edit_video_screen.dart';
import 'package:client/utils/converters.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

import '../../screens/video_playback_screen.dart';

class VideoDescriptionWidget extends StatelessWidget {
  final VideoModel video;

  const VideoDescriptionWidget({Key? key, required this.video})
      : super(key: key);

  void redirectToChannelScreen(BuildContext context){
    Navigator.of(context).pushNamed(ChannelScreen.routeName,
        arguments: {"channelId": video.id});
  }

  @override
  Widget build(BuildContext context) {
    final model = Provider.of<VideoPlaybackScreenViewModel>(context);

    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Padding(
          padding: const EdgeInsets.symmetric(vertical: 8.0),
          child: Text(
            video.title,
            maxLines: 3,
            style: const TextStyle(
              fontSize: 20.0,
              color: Colors.black,
              fontFamily: "Roboto",
              fontWeight: FontWeight.w700,
              decoration: TextDecoration.none,
            ),
          ),
        ),
        Padding(
          padding: const EdgeInsets.symmetric(vertical: 8.0),
          child: Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: [
              Text(
                video.views.toString() + " просмотров",
                style: const TextStyle(
                  fontSize: 16.0,
                  color: Colors.black,
                  fontFamily: "Roboto",
                  fontWeight: FontWeight.w500,
                  decoration: TextDecoration.none,
                ),
              ),
              Text(
                video.uploadTS.format(),
                style: const TextStyle(
                  fontSize: 16.0,
                  color: Colors.black,
                  fontFamily: "Roboto",
                  fontWeight: FontWeight.w500,
                  decoration: TextDecoration.none,
                ),
              ),
            ],
          ),
        ),
        Padding(
          padding: const EdgeInsets.symmetric(vertical: 8.0),
          child: Row(
            children: [
              GestureDetector(
                onTap: () {redirectToChannelScreen(context);},
                child: video.channel.avatarURL == null ||
                        video.channel.avatarURL?.isEmpty == true
                    ? const Icon(
                        Icons.account_circle,
                        size: 30,
                      )
                    : CircleAvatar(
                        backgroundImage:
                            Image.network(video.channel.avatarURL!).image),
              ),
              const SizedBox(
                width: 10.0,
              ),
              Text(
                video.channel.name,
                style: const TextStyle(
                  fontSize: 16.0,
                  color: Colors.black,
                  fontFamily: "Roboto",
                  fontWeight: FontWeight.w500,
                  decoration: TextDecoration.none,
                ),
              ),
            ],
          ),
        ),
        Stack(
          children: [
            video.canEdit ? GestureDetector(
              onTap: () {
                Navigator.of(context).pushNamed(EditVideoScreen.routeName,
                    arguments: {"videoId": video.id});
              },
              child: Column(
                children: const [
                  Text("Настройки"),
                  Icon(
                    Icons.settings,
                    size: 30,
                  ),
                ],
              ),
            ) : Container(),
            Row(
              mainAxisAlignment: MainAxisAlignment.end,
              children: [
                Text(video.likes.toString()),
                GestureDetector(
                  onTap: () => model.onLikeVideoClick(context),
                  child: const Icon(
                    Icons.favorite,
                    size: 30,
                    color: Color.fromRGBO(120, 65, 178, 1.0),
                  ),
                )
              ],
            )
          ],
        ),
      ],
    );
  }
}
