import 'package:flutter/material.dart';
import 'package:video_player/video_player.dart';

class VideoWidget extends StatefulWidget {
  @override
  _VideoAppState createState() => _VideoAppState();
}

class _VideoAppState extends State<VideoWidget> {
  late VideoPlayerController _controller;

  @override
  void initState() {
    super.initState();
    _controller = VideoPlayerController.network(
        'https://rr6---sn-25ge7nse.googlevideo.com/videoplayback?expire=1654291123&ei=UiaaYqqtGZOzlu8P5_WguAo&ip=191.96.67.138&id=o-APrkAFHzz98s3zEj2SQ4TMczjEYZL10ljaILd3TFt7SI&itag=22&source=youtube&requiressl=yes&mh=TP&mm=31%2C26&mn=sn-25ge7nse%2Csn-5hne6nzd&ms=au%2Conr&mv=m&mvi=6&pl=27&initcwndbps=3558750&spc=4ocVC6jHo-KaJ3QWi7UKfpStEKp2t8Q&vprv=1&mime=video%2Fmp4&ns=Wg8BogEaCSDT-nWhbIyT0KoG&cnr=14&ratebypass=yes&dur=5205.240&lmt=1646679096781142&mt=1654269192&fvip=5&fexp=24001373%2C24007246&c=WEB&txp=5532434&n=He0MGC6K39A9wg&sparams=expire%2Cei%2Cip%2Cid%2Citag%2Csource%2Crequiressl%2Cspc%2Cvprv%2Cmime%2Cns%2Ccnr%2Cratebypass%2Cdur%2Clmt&sig=AOq0QJ8wRgIhANkbzf1HV-VsKz1syKzubZ1aErdl3l8OhEsDmtBAJL5ZAiEAu-jofGZUh_0bzrcxum7AzbWdmR6DnxZJuWUae2VytqE%3D&lsparams=mh%2Cmm%2Cmn%2Cms%2Cmv%2Cmvi%2Cpl%2Cinitcwndbps&lsig=AG3C_xAwRQIhALDETKvq8HpQ8FQgWi1mbqESL3nlxhMYug3AVtiRvlozAiAJeOk07GFDeMm64hN9fpTCLkPpbrYDhTfK2bzuDfYxNg%3D%3D&title=%D0%9F%D1%83%D1%82%D0%B5%D1%88%D0%B5%D1%81%D1%82%D0%B2%D0%B8%D0%B5%20%D0%BA%20%D0%BD%D0%B0%D1%87%D0%B0%D0%BB%D1%83%20%D0%B2%D1%80%D0%B5%D0%BC%D1%91%D0%BD')
      //  'https://files.relex.ru/dl/110646a847dd12fc18f3e0aa4897dc6b2038d3/20220502_141026.mp4')
      ..initialize().then((_) {
        // Ensure the first frame is shown after the video is initialized, even before the play button has been pressed.
        setState(() {});
      });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: _controller.value.isInitialized
            ? AspectRatio(
                aspectRatio: _controller.value.aspectRatio,
                child: VideoPlayer(_controller))
            : Container(),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: () {
          setState(() {
            _controller.value.isPlaying
                ? _controller.pause()
                : _controller.play();
          });
        },
        child: Icon(
          _controller.value.isPlaying ? Icons.pause : Icons.play_arrow,
        ),
      ),
    );
  }

  @override
  void dispose() {
    super.dispose();
    _controller.dispose();
  }
}
