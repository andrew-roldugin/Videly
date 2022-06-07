import 'package:client/http/custom_http_client.dart';
import 'package:client/resources/resources.dart';
import 'package:flutter/material.dart';
import 'package:get_it/get_it.dart';
import 'package:video_player/video_player.dart';

class VideoWidget extends StatefulWidget {
  const VideoWidget({Key? key}) : super(key: key);

  @override
  _VideoAppState createState() => _VideoAppState();
}

class _VideoAppState extends State<VideoWidget> {
  late VideoPlayerController _controller;

  @override
  void initState() {
    super.initState();

    final client = GetIt.I<CustomHttpClient>().dioInstance;

    // var data = client
    //     .get(
    //       "https://www.googleapis.com/drive/v2/files/1ijJmkz_KXslgKE0C7j2JVfYaXDnCbpdO?alt=media&key=AIzaSyDqskc2T7bDB_Sizz8fMAb6Dm2jHHn6QNY")
    //     .then((value) {
    //   print(value);
    // });
    _controller = VideoPlayerController.network(
        //'https://rr6---sn-25ge7nse.googlevideo.com/videoplayback?expire=1654291123&ei=UiaaYqqtGZOzlu8P5_WguAo&ip=191.96.67.138&id=o-APrkAFHzz98s3zEj2SQ4TMczjEYZL10ljaILd3TFt7SI&itag=22&source=youtube&requiressl=yes&mh=TP&mm=31%2C26&mn=sn-25ge7nse%2Csn-5hne6nzd&ms=au%2Conr&mv=m&mvi=6&pl=27&initcwndbps=3558750&spc=4ocVC6jHo-KaJ3QWi7UKfpStEKp2t8Q&vprv=1&mime=video%2Fmp4&ns=Wg8BogEaCSDT-nWhbIyT0KoG&cnr=14&ratebypass=yes&dur=5205.240&lmt=1646679096781142&mt=1654269192&fvip=5&fexp=24001373%2C24007246&c=WEB&txp=5532434&n=He0MGC6K39A9wg&sparams=expire%2Cei%2Cip%2Cid%2Citag%2Csource%2Crequiressl%2Cspc%2Cvprv%2Cmime%2Cns%2Ccnr%2Cratebypass%2Cdur%2Clmt&sig=AOq0QJ8wRgIhANkbzf1HV-VsKz1syKzubZ1aErdl3l8OhEsDmtBAJL5ZAiEAu-jofGZUh_0bzrcxum7AzbWdmR6DnxZJuWUae2VytqE%3D&lsparams=mh%2Cmm%2Cmn%2Cms%2Cmv%2Cmvi%2Cpl%2Cinitcwndbps&lsig=AG3C_xAwRQIhALDETKvq8HpQ8FQgWi1mbqESL3nlxhMYug3AVtiRvlozAiAJeOk07GFDeMm64hN9fpTCLkPpbrYDhTfK2bzuDfYxNg%3D%3D&title=%D0%9F%D1%83%D1%82%D0%B5%D1%88%D0%B5%D1%81%D1%82%D0%B2%D0%B8%D0%B5%20%D0%BA%20%D0%BD%D0%B0%D1%87%D0%B0%D0%BB%D1%83%20%D0%B2%D1%80%D0%B5%D0%BC%D1%91%D0%BD')
        //'https://files.relex.ru/dl/110646a847dd12fc18f3e0aa4897dc6b2038d3/20220502_141026.mp4')
        //'https://firebasestorage.googleapis.com/v0/b/videly-2fae9.appspot.com/o/Pexels%20Videos%201722697.mp4?alt=media&token=f92ef89e-c0b3-4f37-a723-c096ff4174c8')
        //'https://drive.google.com/file/d/1ijJmkz_KXslgKE0C7j2JVfYaXDnCbpdO/view?usp=sharing')
        // 'https://disk.yandex.ru/i/cuyyp-TD8J8DUQ')
        // 'https://drive.google.com/file/d/1ijJmkz_KXslgKE0C7j2JVfYaXDnCbpdO/preview?autoplay=1')
        'https://www.googleapis.com/drive/v3/files/1ijJmkz_KXslgKE0C7j2JVfYaXDnCbpdO?alt=media&key=AIzaSyDJwtfG4fyG47mqJXJlO4Th9WUK-909Nbg')
      //'https://storage.yandexcloud.net/videos-backet/Pexels%20Videos%201722697.mp4?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=YCAJEeW5WsX5640S-D-07nuLY%2F20220604%2Fru-central1%2Fs3%2Faws4_request&X-Amz-Date=20220604T125040Z&X-Amz-Expires=7200&X-Amz-Signature=61D6ECB0D5A959869458F27481388F19D4444B87A0045DE325441D72AEDAA34A&X-Amz-SignedHeaders=host')
      ..initialize().then((_) {
        // Ensure the first frame is shown after the video is initialized, even before the play button has been pressed.
        setState(() {});
      });
  }

  @override
  Widget build(BuildContext context) {
    print(_controller);
    return _controller.value.isInitialized
        ? AspectRatio(
            aspectRatio: _controller.value.aspectRatio,
            child: Stack(alignment: AlignmentDirectional.center, children: [
              VideoPlayer(_controller),
              ControlButtonBar(controller: _controller),
            ]))
        : Stack(alignment: AlignmentDirectional.center, children: [
            Image.asset(
              AppImages.noVideoPreview,
              scale: 2,
            ),
            const CircularProgressIndicator(
              color: Colors.black,
              strokeWidth: 3,
            ),
          ]);
  }

  @override
  void dispose() {
    super.dispose();
    _controller.dispose();
  }
}

class ControlButtonBar extends StatefulWidget {
  final VideoPlayerController controller;

  const ControlButtonBar({Key? key, required this.controller})
      : super(key: key);

  @override
  State<ControlButtonBar> createState() => _ControlButtonBarState();
}

class _ControlButtonBarState extends State<ControlButtonBar> {
  @override
  Widget build(BuildContext context) {
    return Column(
      mainAxisAlignment: MainAxisAlignment.center,
      children: [
        ButtonBar(
          alignment: MainAxisAlignment.center,
          children: [
            MaterialButton(
              onPressed: () {
                dispose();
              },
              child: const Icon(
                Icons.skip_previous_rounded,
                color: Colors.grey,
                size: 30,
              ),
            ),
            MaterialButton(
              onPressed: onPlayButtonClick,
              child: Icon(
                widget.controller.value.isPlaying
                    ? Icons.pause
                    : Icons.play_arrow,
                color: Colors.grey,
                size: 30,
              ),
            ),
            MaterialButton(
              onPressed: () {
                widget.controller.dispose();
              },
              child: const Icon(
                Icons.skip_next_rounded,
                color: Colors.grey,
                size: 30,
              ),
            )
          ],
        ),
      ],
    );
  }

  void onPlayButtonClick() {
    setState(() {
      widget.controller.value.isPlaying
          ? widget.controller.pause()
          : widget.controller.play();
    });
  }
}
