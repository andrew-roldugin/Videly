import 'dart:developer';

import 'package:client/domain/models/entity/video_model.dart';
import 'package:client/pages/error_page.dart';
import 'package:client/services/video_service.dart';
import 'package:client/widgets/common/modal_bottom_sheet.dart';
import 'package:client/widgets/video/comment_list_widget.dart';
import 'package:client/widgets/video/video.dart';
import 'package:client/widgets/video/video_description_widget.dart';
import 'package:flutter/material.dart';
import 'package:flutter/rendering.dart';
import 'package:flutter/services.dart';
import 'package:get_it/get_it.dart';
import 'package:keyboard_attachable/keyboard_attachable.dart';
import 'package:provider/provider.dart';

class VideoPlaybackScreenViewModel extends ChangeNotifier {
  late bool isLoading = false;
  String err = "";
  late final VideoModel _video;
  late final VideoService _videoService;

  VideoPlaybackScreenViewModel(String videoId) {
    _videoService = GetIt.I<VideoService>();
    getByVideoId(videoId);
  }

  void getByVideoId(String videoId) async {
    isLoading = true;
    notifyListeners();

    await _videoService.getVideoData(videoId: videoId).then((value) {
      if (value != null) {
        _video = value;
      }
    });

    isLoading = false;
    notifyListeners();
  }

  void onLikeVideoClick(BuildContext context) async {
    await _videoService.likeVideo(videoId: _video.id).then((value) {
      if (value != null) {
        MyBottomSheet.show(context,
            "Пользователь " + (value == 1 ? "поставил" : "удалил") + " лайк");
        notifyListeners();
      }
    });
  }
}

class VideoPlaybackScreen extends StatelessWidget {
  static const routeName = "/main/video";

  const VideoPlaybackScreen({Key? key}) : super(key: key);

  static Widget create({required String videoId}) {
    return ChangeNotifierProvider(
      create: (_) => VideoPlaybackScreenViewModel(videoId),
      child: const VideoPlaybackScreen(),
    );
  }

  @override
  Widget build(BuildContext context) {
    final model = context.watch<VideoPlaybackScreenViewModel>();

    if (model.err.isNotEmpty) {
      return ErrorPage(message: model.err);
    }

    if (model.isLoading) {
      return SafeArea(
        child: Scaffold(
          body: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: const [
              Padding(
                padding: EdgeInsets.all(8.0),
                child: Text("Страница загружается"),
              ),
              Center(
                child: CircularProgressIndicator(),
              ),
            ],
          ),
        ),
      );
    }

    return SafeArea(
      bottom: true,
      child: Scaffold(
        body: Column(
          children: [
            VideoWidget(
              video: model._video,
            ),
            Column(
              children: [
                Padding(
                  padding: const EdgeInsets.symmetric(horizontal: 15.0),
                  child: VideoDescriptionWidget(video: model._video),
                ),
                Padding(
                  padding: const EdgeInsets.symmetric(horizontal: 15.0),
                  child: Column(
                    children: [
                      const Text("Комментарии"),
                      TextFormField(
                        autovalidateMode: AutovalidateMode.onUserInteraction,
                        keyboardType: TextInputType.text,
                        validator: (v) => v == null || v.isEmpty
                            ? "Напишите что-нибудь..."
                            : null,
                        decoration: InputDecoration(
                          fillColor: Colors.white,
                          filled: true,
                          suffixIcon: MaterialButton(
                            child: const Icon(Icons.send),
                            onPressed: () {
                              print("send");
                            },
                          ),
                          hintText: "Комментарий",
                          border: OutlineInputBorder(
                            borderRadius: BorderRadius.circular(10),
                          ),
                        ),
                        onChanged: (v) {},
                      ),
                    ],
                  ),
                ),
              ],
            ),
            Expanded(
              child: CommentListWidget.create(model._video.id),
            )
          ],
        ),
      ),
    );
  }
}
