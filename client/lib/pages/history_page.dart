import 'package:flutter/material.dart';

class HistoryPage extends StatefulWidget {
  const HistoryPage({Key? key}) : super(key: key);

  @override
  State<HistoryPage> createState() => _HistoryPageState();
}

class _HistoryPageState extends State<HistoryPage> {
  @override
  Widget build(BuildContext context) {
    // return SafeArea(
    //   child: Row(
    //     mainAxisAlignment: MainAxisAlignment.center,
    //     children: const [Text("История просмотров")],
    //   ),
    // );
    return SafeArea(
      child: Column(
        children: [
          SizedBox(height: 10),
          Container(
            child: Text(
              'История просмотров',
              textAlign: TextAlign.center,
              style: Theme.of(context).textTheme.headline3,
            ),
          ),
          SizedBox(height: 10),
          Expanded(
            child: _VideoList(),
          ),
        ],
      ),
    );
  }
}

class _VideoList extends StatelessWidget {
  const _VideoList({
    Key? key,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return ListView.builder(
      padding: const EdgeInsets.only(bottom: 50),
      itemCount: 10,
      itemExtent: 150,
      itemBuilder: (BuildContext context, int index) {
        return Padding(
          padding: const EdgeInsets.symmetric(horizontal: 15, vertical: 5),
          child: Stack(
            children: [
              Container(
                decoration: BoxDecoration(
                  color: Colors.white,
                  border: Border.all(color: Colors.black.withOpacity(0.2)),
                  borderRadius: BorderRadius.all(Radius.circular(10)),
                  boxShadow: [
                    BoxShadow(
                      color: Colors.black.withOpacity(0.1),
                      blurRadius: 8,
                      offset: Offset(0, 2),
                    )
                  ],
                ),
                clipBehavior: Clip.hardEdge,
                child: Row(
                  children: [
                    Padding(
                      padding: const EdgeInsets.symmetric(horizontal: 15.0),
                      child: Image.asset("assets/images/no_video_preview.png",
                          width: 80, height: 80),
                    ),
                    SizedBox(
                      height: 15,
                    ),
                    Expanded(
                        child: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        SizedBox(
                          height: 20,
                        ),
                        Text(
                          'Название видео',
                          style: TextStyle(fontWeight: FontWeight.bold),
                          maxLines: 1,
                          overflow: TextOverflow.ellipsis,
                        ),
                        SizedBox(
                          height: 5,
                        ),
                        Text(
                          "15:00 04.06.2022",
                          style: TextStyle(color: Colors.grey),
                          maxLines: 1,
                          overflow: TextOverflow.ellipsis,
                        ),
                        SizedBox(
                          height: 20,
                        ),
                        Text(
                          //"Описание тестового видео",
                          'dkladsmsalkdmklasmklcmwlekcndqwmimeclcsjlqneidcqwxm csaxml kdsmklaqwcl,,',
                          maxLines: 2,
                          overflow: TextOverflow.ellipsis,
                        ),
                      ],
                    )),
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
                    print(111);
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
