import 'package:client/domain/models/entity/comment_model.dart';
import 'package:client/screens/channel_screen.dart';
import 'package:client/services/comment_service.dart';
import 'package:client/utils/converters.dart';
import 'package:flutter/material.dart';
import 'package:get_it/get_it.dart';
import 'package:provider/provider.dart';
import 'package:intl/intl.dart';

class CommentListViewModel extends ChangeNotifier {
  late bool isLoading = false;
  late final _comments = <CommentModel>[];
  late final CommentService commentService;

  CommentListViewModel(String videoId) {
    commentService = GetIt.I<CommentService>();
    loadByVideoId(videoId);
  }

  void loadByVideoId(String videoId) async {
    isLoading = true;
    notifyListeners();

    await commentService.loadAllCommentsAtVideo(videoId: videoId).then((value) {
      _comments.addAll(value!);
    });

    isLoading = false;
    notifyListeners();
  }


  void redirectToChannelScreen(BuildContext context, String channelId) {
    Navigator.of(context).pushNamed(ChannelScreen.routeName,
        arguments: {"channelId": channelId});
  }
}

class CommentListWidget extends StatelessWidget {
  final String videoId;

  const CommentListWidget({Key? key, required this.videoId}) : super(key: key);

  static Widget create(String videoId) {
    return ChangeNotifierProvider(
      create: (_) => CommentListViewModel(videoId),
      child: CommentListWidget(videoId: videoId),
    );
  }

  @override
  Widget build(BuildContext context) {
    final model = context.watch<CommentListViewModel>();

    if (model.isLoading) {
      return const Center(child: CircularProgressIndicator());
    }

    if (model._comments.isEmpty) {
      return const Center(child: Text("Пока нет комментариев"));
    }

    return ListView.builder(
        itemCount: model._comments.length,
        itemBuilder: (BuildContext ctx, int index) {
          return Padding(
            padding: const EdgeInsets.symmetric(vertical: 8.0, horizontal: 5.0),
            child: _CommentItem(comment: model._comments[index]),
          );
        });

    // return Selector<CommentListViewModel, List<CommentModel>>(
    //   selector: (context, model) {
    //     model.loadByVideoId(videoId);
    //     return model._comments;
    //   },
    //   shouldRebuild: (previous, next) => next.length != previous.length,
    //   builder:  (context, comments, _) {
    //     return ListView.builder(
    //         itemCount: 25,
    //         itemBuilder: (BuildContext ctx, int index) {
    //       return _CommentItem(comment: comments[index]);
    //     });
    //   }
    // );
  }
}

class _CommentItem extends StatelessWidget {
  final CommentModel comment;

  const _CommentItem({Key? key, required this.comment}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    final model = context.read<CommentListViewModel>();
    return Container(
      padding: const EdgeInsets.all(10.0),
      decoration: BoxDecoration(
        color: Colors.white,
        border: Border.all(color: Colors.black.withOpacity(0.2)),
        borderRadius: const BorderRadius.all(Radius.circular(10)),
        boxShadow: [
          BoxShadow(
            color: Colors.black.withOpacity(0.1),
            blurRadius: 8,
            offset: const Offset(0, 2),
          )
        ],
      ),
      clipBehavior: Clip.hardEdge,
      child: Row(
        children: [
          GestureDetector(
            // shape: const CircleBorder(side: BorderSide(width: 100)),
            onTap: () {
              model.redirectToChannelScreen(context, comment.channel!.id!);
            },
            child: comment.channel?.avatarURL != null &&
                    comment.channel?.avatarURL?.isNotEmpty == true
                ? CircleAvatar(
                    backgroundImage: Image.network(
                      comment.channel!.avatarURL!,
                    ).image,
                  )
                : const Icon(
                    Icons.account_circle,
                    size: 50,
                  ),
            // ? Container(
            //     decoration: const BoxDecoration(
            //       borderRadius: BorderRadius.all(Radius.circular(50)),
            //     ),
            //     constraints: const BoxConstraints(
            //       maxWidth: 50,
            //     ),
            //     clipBehavior: Clip.hardEdge,
            //     child: Image.network(
            //       comment.channel!.avatarURL!,
            //       // width: 50,
            //       // height: 50,
            //     ),
            //   )
          ),
          const SizedBox(
            width: 10,
          ),
          Expanded(
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                Row(
                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                  children: [
                    GestureDetector(
                      onTap: () => model.redirectToChannelScreen(context, comment.channel!.id!),
                      child: Text(
                        comment.channel!.name,
                        style: Theme.of(context).textTheme.bodyText2,
                        maxLines: 1,
                        overflow: TextOverflow.ellipsis,
                      ),
                    ),
                    Text(
                      comment.ts
                          .format(DateFormat.yMMMd("ru_RU").pattern!, "ru_RU"),
                      style: const TextStyle(
                        fontSize: 16.0,
                        color: Colors.black,
                        fontFamily: "Roboto",
                        fontWeight: FontWeight.w500,
                        fontStyle: FontStyle.italic,
                        decoration: TextDecoration.none,
                      ),
                      maxLines: 1,
                      overflow: TextOverflow.ellipsis,
                    ),
                  ],
                ),
                const SizedBox(
                  height: 15,
                ),
                Text(
                  comment.content,
                  // overflow: TextOverflow.visible,
                  // maxLines: 1,
                  style: const TextStyle(
                      fontSize: 16.0,
                      color: Colors.black,
                      fontFamily: "Roboto",
                      fontWeight: FontWeight.w500,
                      decoration: TextDecoration.none),
                ),
              ],
            ),
          )
        ],
      ),
    );
  }
}
