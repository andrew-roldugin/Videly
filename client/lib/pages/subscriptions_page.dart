import 'package:client/domain/models/entity/channel_model.dart';
import 'package:client/domain/models/entity/history_model.dart';
import 'package:client/domain/models/entity/subscription_model.dart';
import 'package:client/domain/models/entity/video_model.dart';
import 'package:client/utils/converters.dart';
import 'package:client/widgets/subscription/subsription_list_widget.dart';
import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';

class SubscriptionsPage extends StatefulWidget {
  const SubscriptionsPage({Key? key}) : super(key: key);

  @override
  State<SubscriptionsPage> createState() => _SubscriptionsPageState();
}

class _SubscriptionsPageState extends State<SubscriptionsPage> {
  final subscrs = <SubscriptionModel>[
    SubscriptionModel(
      id: "id",
      followedSince: DateTime.parse("2022-05-06"),
      channel: ChannelModel(
        id: "id",
        name: "Команда Videly",
        avatarURL: "avatarURL",
        headerURL: "headerURL",
        about: "about",
        createdAt: DateTime.now(),
        deleted: false,
        allowComments: false,
        allowRating: false,
      ),
    ),
    SubscriptionModel(
      id: "id",
      followedSince: DateTime.parse("2022-05-01"),
      channel: ChannelModel(
        id: "id",
        name: "DEV.io | Разработка на Flutter",
        avatarURL: "avatarURL",
        headerURL: "headerURL",
        about: "about",
        createdAt: DateTime.now(),
        deleted: false,
        allowComments: false,
        allowRating: false,
      ),
    ),
  ];

  @override
  Widget build(BuildContext context) {
    return ListView.builder(
      itemCount: subscrs.length,
      itemExtent: 80,
      itemBuilder: (BuildContext context, int index) {
        final item = subscrs[index];
        return Padding(
          padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 5),
          child: Stack(
            children: [
              Container(
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
                    Padding(
                      padding: const EdgeInsets.symmetric(horizontal: 10.0),
                      child: Icon(
                        Icons.account_circle,
                        size: 50,
                      ),
                    ),
                    // Image(
                    //     image:
                    //     AssetImage('assets/images/user_logo.png'),
                    //     height: 50,
                    //     width: 50,
                    //     ),
                    SizedBox(
                      height: 15,
                    ),
                    Expanded(
                      child: Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        mainAxisAlignment: MainAxisAlignment.center,
                        children: [
                          // SizedBox(
                          // height: 20,
                          // width: 10,
                          // ),
                          Text(
                            item.channel.name,
                            style: TextStyle(fontWeight: FontWeight.bold),
                            maxLines: 1,
                            overflow: TextOverflow.ellipsis,
                          ),
                          SizedBox(
                            height: 5,
                          ),
                          Text(
                            "Отслеживает с ${item.followedSince.format()}",
                            style: TextStyle(color: Colors.grey),
                            maxLines: 1,
                            overflow: TextOverflow.ellipsis,
                          ),
                          // SizedBox(
                          //   height: 20,
                          // ),
                          // Text(
                          //   "Описание тестового видео",
                          //   maxLines: 2,
                          //   overflow: TextOverflow.ellipsis,
                          // ),
                        ],
                      ),
                    ),
                    SizedBox(
                      width: 10,
                    ),
                  ],
                ),
              ),
              Material(
                color: Colors.transparent,
                child: InkWell(
                  borderRadius: BorderRadius.all(Radius.circular(10)),
                  onTap: () {
                    print(11);
                  },
                ),
              ),
            ],
          ),
        );
      },
    );
  }
}
