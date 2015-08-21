import com.mickey305.commons.analysis.json.JSONAnalyzer;
import com.mickey305.commons.analysis.json.JSONPicker;
import com.mickey305.commons.analysis.json.JSONToken;
import com.mickey305.commons.analysis.json.JSONTokenListBuilder;
import com.mickey305.commons.datastructure.RingBuffer;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.LinkedList;

public class ShowSample {
    public static final String TAG = ShowSample.class.getName();

    public ShowSample() {}

    public void debugJSONAnalyzer() {
        String[] jsonArray = new String[2];
        jsonArray[0] = "{\"results\":[{\n" +
                "  \"name\" : { \"first\" : \"太郎\", \"last\" : \"技評\" }, \n" +
                "  \"mail\" : \"taro@example.jp\", \n" +
                "  \"todo\" : { \"work\" : \"hogehoge\", \"limit\" : \"2012/02/13\" }\n" +
                "}, \n" +
                "{\n" +
                "  \"name\" : { \"first\" : \"次郎\", \"last\" : \"技術\" }, \n" +
                "  \"mail\" : \"jiro@example.jp\", \n" +
                "  \"todo\" : { \"work\" : [123,2,null], \"limit\" : \"2012/02/15\" }\n" +
                "}, \n" +
                "{\n" +
                "  \"name\" : { \"first\" : \"花子\", \"last\" : \"評論\" }, \n" +
                "  \"mail\" : \"hanako@example.jp\", \n" +
                "  \"todo\" : { \"work\" : \"hogepiyo\", \"limit\" : \"2012/02/28\" }\n" +
                "}]}";
        jsonArray[1] = "{\"result\":[{\n" +
                "  \"name\" : { \"first\" : \"太郎\", \"last\" : \"技評\" }, \n" +
                "  \"mail\" : \"taro@example.jp\", \n" +
                "  \"todo\" : { \"work\" : \"hogehoge\", \"limit\" : \"2012/02/13\" }\n" +
                "}, \n" +
                "{\n" +
                "  \"name\" : { \"first\" : \"次郎\", \"last\" : \"技術\" }, \n" +
                "  \"mail\" : \"jiro@example.jp\", \n" +
                "  \"todo\" : { \"work\" : \"hogehoge\", \"limit\" : \"2012/02/15\" }\n" +
                "}, \n" +
                "{\n" +
                "  \"name\" : { \"first\" : \"花子\", \"last\" : \"評論\" }, \n" +
                "  \"mail\" : \"hanako@example.jp\", \n" +
                "  \"todo\" : { \"work\" : \"hogepiyo\", \"limit\" : \"2012/02/28\" }\n" +
                "}]}";
//        jsonArray[0] = "{ \"work\" : [123,2,null], \"limit\" : \"2012/02/13\" }";
//        jsonArray[1] = "{ \"work\" : [123,2,null], \"limit\" : \"2012/02/13\" }";
        JSONObject[] jsonObjectArray = {new JSONObject(jsonArray[0]), new JSONObject(jsonArray[1])};

        int[] deep = new int[1];
        String jsonString = "{\"results\":{\"faceRecognition\":{\"width\":600,\"height\":900,\"detectionFaceNumber\":1,\"detectionFaceInfo\":[{\"faceCoordinates\":{\"faceConfidence\":137,\"faceFrameLeftX\":311,\"faceFrameRightX\":437,\"faceFrameTopY\":249,\"faceFrameBottomY\":375,\"leftEyeX\":339,\"leftEyeY\":299,\"rightEyeX\":395,\"rightEyeY\":281},\"facePartsCoordinates\":{\"boundingRectangleLeftUpper\":{\"x\":311,\"y\":249},\"boundingRectangleRightUpper\":{\"x\":437,\"y\":249},\"boundingRectangleRightLower\":{\"x\":437,\"y\":375},\"boundingRectangleLeftLower\":{\"x\":311,\"y\":375},\"leftBlackEyeCenter\":{\"x\":339,\"y\":299},\"rightBlackEyeCenter\":{\"x\":395,\"y\":281},\"basicCoordinateNumber\":6,\"leftCheek1\":{\"x\":317,\"y\":308},\"leftCheek2\":{\"x\":322,\"y\":326},\"leftCheek3\":{\"x\":329,\"y\":343},\"leftCheek4\":{\"x\":338,\"y\":360},\"leftCheek5\":{\"x\":352,\"y\":376},\"leftCheek6\":{\"x\":368,\"y\":389},\"leftCheek7\":{\"x\":385,\"y\":397},\"chin\":{\"x\":403,\"y\":396},\"rightCheek7\":{\"x\":421,\"y\":385},\"rightCheek6\":{\"x\":433,\"y\":368},\"rightCheek5\":{\"x\":440,\"y\":350},\"rightCheek4\":{\"x\":443,\"y\":329},\"rightCheek3\":{\"x\":441,\"y\":313},\"rightCheek2\":{\"x\":437,\"y\":294},\"rightCheek1\":{\"x\":430,\"y\":273},\"parietal\":{\"x\":346,\"y\":206},\"rightEyebrowOutsideEnd\":{\"x\":413,\"y\":265},\"rightEyebrowUpperOutside\":{\"x\":398,\"y\":257},\"rightEyebrowUpperInside\":{\"x\":381,\"y\":264},\"rightEyebrowInsideEnd\":{\"x\":372,\"y\":276},\"rightEyebrowLowerInside\":{\"x\":385,\"y\":271},\"rightEyebrowLowerOutside\":{\"x\":399,\"y\":265},\"leftEyebrowOutsideEnd\":{\"x\":318,\"y\":293},\"leftEyebrowUpperOutside\":{\"x\":326,\"y\":280},\"leftEyebrowUpperInside\":{\"x\":343,\"y\":278},\"leftEyebrowInsideEnd\":{\"x\":353,\"y\":283},\"leftEyebrowLowerInside\":{\"x\":342,\"y\":286},\"leftEyebrowLowerOutside\":{\"x\":329,\"y\":287},\"leftEyeOutsideEnd\":{\"x\":328,\"y\":303},\"leftEyeUpperOutside\":{\"x\":333,\"y\":298},\"leftEyeUpperCenter\":{\"x\":338,\"y\":295},\"leftEyeUpperInside\":{\"x\":344,\"y\":295},\"leftEyeInsideEnd\":{\"x\":352,\"y\":298},\"leftEyeLowerInside\":{\"x\":346,\"y\":301},\"leftEyeLowerCenter\":{\"x\":340,\"y\":303},\"leftEyeLowerOutside\":{\"x\":334,\"y\":304},\"rightEyeOutsideEnd\":{\"x\":408,\"y\":277},\"rightEyeUpperOutside\":{\"x\":400,\"y\":276},\"rightEyeUpperCenter\":{\"x\":393,\"y\":277},\"rightEyeUpperInside\":{\"x\":387,\"y\":281},\"rightEyeInsideEnd\":{\"x\":383,\"y\":288},\"rightEyeLowerInside\":{\"x\":389,\"y\":286},\"rightEyeLowerCenter\":{\"x\":396,\"y\":285},\"rightEyeLowerOutside\":{\"x\":402,\"y\":281},\"noseLeftLineUpper\":{\"x\":359,\"y\":295},\"noseLeftLineCenter\":{\"x\":361,\"y\":314},\"noseLeftLineLower0\":{\"x\":356,\"y\":330},\"noseLeftLineLower1\":{\"x\":363,\"y\":336},\"noseBottomCenter\":{\"x\":378,\"y\":336},\"noseRightLineLower1\":{\"x\":393,\"y\":326},\"noseRightLineLower0\":{\"x\":395,\"y\":318},\"noseRightLineCenter\":{\"x\":381,\"y\":307},\"noseRightLineUpper\":{\"x\":373,\"y\":291},\"nostrilsLeft\":{\"x\":367,\"y\":335},\"nostrilsRight\":{\"x\":387,\"y\":328},\"noseTip\":{\"x\":373,\"y\":326},\"noseCenterLine0\":{\"x\":371,\"y\":310},\"noseCenterLine1\":{\"x\":366,\"y\":293},\"mouthLeftEnd\":{\"x\":356,\"y\":349},\"upperLipUpperLeftOutside\":{\"x\":366,\"y\":346},\"upperLipUpperLeftInside\":{\"x\":374,\"y\":344},\"mouthUpperPart\":{\"x\":381,\"y\":343},\"upperLipUpperRightInside\":{\"x\":388,\"y\":339},\"upperLipUpperRightOutside\":{\"x\":398,\"y\":336},\"mouthRightEnd\":{\"x\":410,\"y\":333},\"lowerLipLowerRightOutside\":{\"x\":407,\"y\":346},\"lowerLipLowerRightInside\":{\"x\":399,\"y\":355},\"mouthLowerPart\":{\"x\":388,\"y\":361},\"lowerLipLowerLeftInside\":{\"x\":377,\"y\":362},\"lowerLipLowerLeftOutside\":{\"x\":367,\"y\":359},\"upperLipLowerLeft\":{\"x\":372,\"y\":348},\"upperLipLowerCenter\":{\"x\":382,\"y\":346},\"upperLipLowerRight\":{\"x\":394,\"y\":341},\"lowerLipUpperRight\":{\"x\":398,\"y\":347},\"lowerLipUpperCenter\":{\"x\":385,\"y\":354},\"lowerLipUpperLeft\":{\"x\":373,\"y\":355},\"mouthCenter\":{\"x\":384,\"y\":350},\"detectionPointTotal\":83},\"blinkJudge\":{\"blinkLevel\":{\"leftEye\":100,\"rightEye\":103}},\"ageJudge\":{\"ageResult\":18,\"ageScore\":642},\"genderJudge\":{\"genderResult\":1,\"genderScore\":1000},\"faceAngleJudge\":{\"pitch\":-13,\"yaw\":8,\"roll\":-18,\"faceAngleVector\":{\"x\":-3,\"y\":12},\"gazeVector\":{\"x\":-3,\"y\":-11}},\"smileJudge\":{\"smileLevel\":59},\"registrationFaceInfo\":{\"meta\":{},\"faceId\":3,\"imagePath\":\"http://sample.hogehoge.example.jp/webapi/public/90a9cd77d26db5331d32a672baa66d94/20150726223647181_PC.jpg\"}}],\"errorInfo\":\"\",\"errorMessage\":\"\"}}}";
        JSONAnalyzer.getTreeLayerDeepest(deep, jsonObjectArray[0]);
        Log.d(TAG, "token deep - " + deep[0]);

        LinkedList<JSONToken> list = new LinkedList<>();
        JSONAnalyzer.matchedKeyList(list, jsonObjectArray[0], jsonObjectArray[1]);
        for(JSONToken token: list) {
            Log.d(TAG, token.getValue().to_s());
        }

        LinkedList<Integer> intlist = new LinkedList<>();
        JSONAnalyzer.getTreeLayerList(intlist, jsonString);
        for(int i: intlist) {
            String graph = "";
            for (int j=0; j < i; j++) { graph += "-"; }
            Log.d(TAG, graph);
        }

        LinkedList<JSONToken> symbols = new LinkedList<>();
        JSONAnalyzer.createSymbolList(symbols, jsonObjectArray[0]);
        String symbol ="";
        for(JSONToken token: symbols) {
            symbol += token.getValue().to_s();
        }
        Log.d(TAG, symbol);

        Log.d(TAG, JSONAnalyzer.sameStructure(jsonObjectArray[0], jsonObjectArray[1])+"");
    }

    public void debugJSONPicker() {
        String jsonString = "{\"results\":{\"faceRecognition\":{\"width\":600,\"height\":900,\"detectionFaceNumber\":1,\"detectionFaceInfo\":[{\"faceCoordinates\":{\"faceConfidence\":137,\"faceFrameLeftX\":311,\"faceFrameRightX\":437,\"faceFrameTopY\":249,\"faceFrameBottomY\":375,\"leftEyeX\":339,\"leftEyeY\":299,\"rightEyeX\":395,\"rightEyeY\":281},\"facePartsCoordinates\":{\"boundingRectangleLeftUpper\":{\"x\":311,\"y\":249},\"boundingRectangleRightUpper\":{\"x\":437,\"y\":249},\"boundingRectangleRightLower\":{\"x\":437,\"y\":375},\"boundingRectangleLeftLower\":{\"x\":311,\"y\":375},\"leftBlackEyeCenter\":{\"x\":339,\"y\":299},\"rightBlackEyeCenter\":{\"x\":395,\"y\":281},\"basicCoordinateNumber\":6,\"leftCheek1\":{\"x\":317,\"y\":308},\"leftCheek2\":{\"x\":322,\"y\":326},\"leftCheek3\":{\"x\":329,\"y\":343},\"leftCheek4\":{\"x\":338,\"y\":360},\"leftCheek5\":{\"x\":352,\"y\":376},\"leftCheek6\":{\"x\":368,\"y\":389},\"leftCheek7\":{\"x\":385,\"y\":397},\"chin\":{\"x\":403,\"y\":396},\"rightCheek7\":{\"x\":421,\"y\":385},\"rightCheek6\":{\"x\":433,\"y\":368},\"rightCheek5\":{\"x\":440,\"y\":350},\"rightCheek4\":{\"x\":443,\"y\":329},\"rightCheek3\":{\"x\":441,\"y\":313},\"rightCheek2\":{\"x\":437,\"y\":294},\"rightCheek1\":{\"x\":430,\"y\":273},\"parietal\":{\"x\":346,\"y\":206},\"rightEyebrowOutsideEnd\":{\"x\":413,\"y\":265},\"rightEyebrowUpperOutside\":{\"x\":398,\"y\":257},\"rightEyebrowUpperInside\":{\"x\":381,\"y\":264},\"rightEyebrowInsideEnd\":{\"x\":372,\"y\":276},\"rightEyebrowLowerInside\":{\"x\":385,\"y\":271},\"rightEyebrowLowerOutside\":{\"x\":399,\"y\":265},\"leftEyebrowOutsideEnd\":{\"x\":318,\"y\":293},\"leftEyebrowUpperOutside\":{\"x\":326,\"y\":280},\"leftEyebrowUpperInside\":{\"x\":343,\"y\":278},\"leftEyebrowInsideEnd\":{\"x\":353,\"y\":283},\"leftEyebrowLowerInside\":{\"x\":342,\"y\":286},\"leftEyebrowLowerOutside\":{\"x\":329,\"y\":287},\"leftEyeOutsideEnd\":{\"x\":328,\"y\":303},\"leftEyeUpperOutside\":{\"x\":333,\"y\":298},\"leftEyeUpperCenter\":{\"x\":338,\"y\":295},\"leftEyeUpperInside\":{\"x\":344,\"y\":295},\"leftEyeInsideEnd\":{\"x\":352,\"y\":298},\"leftEyeLowerInside\":{\"x\":346,\"y\":301},\"leftEyeLowerCenter\":{\"x\":340,\"y\":303},\"leftEyeLowerOutside\":{\"x\":334,\"y\":304},\"rightEyeOutsideEnd\":{\"x\":408,\"y\":277},\"rightEyeUpperOutside\":{\"x\":400,\"y\":276},\"rightEyeUpperCenter\":{\"x\":393,\"y\":277},\"rightEyeUpperInside\":{\"x\":387,\"y\":281},\"rightEyeInsideEnd\":{\"x\":383,\"y\":288},\"rightEyeLowerInside\":{\"x\":389,\"y\":286},\"rightEyeLowerCenter\":{\"x\":396,\"y\":285},\"rightEyeLowerOutside\":{\"x\":402,\"y\":281},\"noseLeftLineUpper\":{\"x\":359,\"y\":295},\"noseLeftLineCenter\":{\"x\":361,\"y\":314},\"noseLeftLineLower0\":{\"x\":356,\"y\":330},\"noseLeftLineLower1\":{\"x\":363,\"y\":336},\"noseBottomCenter\":{\"x\":378,\"y\":336},\"noseRightLineLower1\":{\"x\":393,\"y\":326},\"noseRightLineLower0\":{\"x\":395,\"y\":318},\"noseRightLineCenter\":{\"x\":381,\"y\":307},\"noseRightLineUpper\":{\"x\":373,\"y\":291},\"nostrilsLeft\":{\"x\":367,\"y\":335},\"nostrilsRight\":{\"x\":387,\"y\":328},\"noseTip\":{\"x\":373,\"y\":326},\"noseCenterLine0\":{\"x\":371,\"y\":310},\"noseCenterLine1\":{\"x\":366,\"y\":293},\"mouthLeftEnd\":{\"x\":356,\"y\":349},\"upperLipUpperLeftOutside\":{\"x\":366,\"y\":346},\"upperLipUpperLeftInside\":{\"x\":374,\"y\":344},\"mouthUpperPart\":{\"x\":381,\"y\":343},\"upperLipUpperRightInside\":{\"x\":388,\"y\":339},\"upperLipUpperRightOutside\":{\"x\":398,\"y\":336},\"mouthRightEnd\":{\"x\":410,\"y\":333},\"lowerLipLowerRightOutside\":{\"x\":407,\"y\":346},\"lowerLipLowerRightInside\":{\"x\":399,\"y\":355},\"mouthLowerPart\":{\"x\":388,\"y\":361},\"lowerLipLowerLeftInside\":{\"x\":377,\"y\":362},\"lowerLipLowerLeftOutside\":{\"x\":367,\"y\":359},\"upperLipLowerLeft\":{\"x\":372,\"y\":348},\"upperLipLowerCenter\":{\"x\":382,\"y\":346},\"upperLipLowerRight\":{\"x\":394,\"y\":341},\"lowerLipUpperRight\":{\"x\":398,\"y\":347},\"lowerLipUpperCenter\":{\"x\":385,\"y\":354},\"lowerLipUpperLeft\":{\"x\":373,\"y\":355},\"mouthCenter\":{\"x\":384,\"y\":350},\"detectionPointTotal\":83},\"blinkJudge\":{\"blinkLevel\":{\"leftEye\":100,\"rightEye\":103}},\"ageJudge\":{\"ageResult\":18,\"ageScore\":642},\"genderJudge\":{\"genderResult\":1,\"genderScore\":1000},\"faceAngleJudge\":{\"pitch\":-13,\"yaw\":8,\"roll\":-18,\"faceAngleVector\":{\"x\":-3,\"y\":12},\"gazeVector\":{\"x\":-3,\"y\":-11}},\"smileJudge\":{\"smileLevel\":59},\"registrationFaceInfo\":{\"meta\":{},\"faceId\":3,\"imagePath\":\"http://sample.hogehoge.example.jp/webapi/public/90a9cd77d26db5331d32a672baa66d94/20150726223647181_PC.jpg\"}}],\"errorInfo\":\"\",\"errorMessage\":\"\"}}}";
//        String jsonString = "[\"fuga\",{},[{\"test\":\"value100\", \"test2\":{\"test30\":\"value60\"}}, 1234, \"hogehoge\"],\"hoge\", {\n" +
//                "  \"name\" : { \"first\" : \"太郎\", \"last\" : \"技評\" }, \n" +
//                "  \"mail\" : \"taro@example.jp\", \n" +
//                "  \"todo\" : { \"work\" : [123,2,null], \"limit\" : \"2012/02/13\" }\n" +
//                "}, \n" +
//                "{\n" +
//                "  \"name\" : { \"first\" : \"次郎\", \"last\" : \"技術\" }, \n" +
//                "  \"mail\" : \"jiro@example.jp\", \n" +
//                "  \"todo\" : { \"work\" : \"hogehoge\", \"limit\" : \"2012/02/15\" }\n" +
//                "}, \n" +
//                "{\n" +
//                "  \"name\" : { \"first\" : \"花子\", \"last\" : \"評論\" }, \n" +
//                "  \"mail\" : \"hanako@example.jp\", \n" +
//                "  \"todo\" : { \"work\" : \"hogepiyo\", \"limit\" : \"2012/02/28\" }\n" +
//                "},\"fugafuga\"]";

//        JSONArray json = new JSONArray(jsonString);
        JSONObject json = new JSONObject(jsonString);

        JSONPicker picker = new JSONPicker(json);
//        LinkedList<JSONToken> list = picker.getValues("name", "first");
        LinkedList<JSONToken> list = picker.getAllValueHashList();
        Log.d(TAG, "list size - "+list.size());
        Iterator<JSONToken> iterator = list.iterator();
        while(iterator.hasNext()) {
            JSONToken token = iterator.next();
            Log.d(TAG, token.getValue().to_s() + "\t" + token.getType().toString());
        }

    }

    public void debugJSONTokenListBuilder() {
//        String jsonString = "{\"results\":{\"faceRecognition\":{\"width\":600,\"height\":900,\"detectionFaceNumber\":1,\"detectionFaceInfo\":[{\"faceCoordinates\":{\"faceConfidence\":137,\"faceFrameLeftX\":311,\"faceFrameRightX\":437,\"faceFrameTopY\":249,\"faceFrameBottomY\":375,\"leftEyeX\":339,\"leftEyeY\":299,\"rightEyeX\":395,\"rightEyeY\":281},\"facePartsCoordinates\":{\"boundingRectangleLeftUpper\":{\"x\":311,\"y\":249},\"boundingRectangleRightUpper\":{\"x\":437,\"y\":249},\"boundingRectangleRightLower\":{\"x\":437,\"y\":375},\"boundingRectangleLeftLower\":{\"x\":311,\"y\":375},\"leftBlackEyeCenter\":{\"x\":339,\"y\":299},\"rightBlackEyeCenter\":{\"x\":395,\"y\":281},\"basicCoordinateNumber\":6,\"leftCheek1\":{\"x\":317,\"y\":308},\"leftCheek2\":{\"x\":322,\"y\":326},\"leftCheek3\":{\"x\":329,\"y\":343},\"leftCheek4\":{\"x\":338,\"y\":360},\"leftCheek5\":{\"x\":352,\"y\":376},\"leftCheek6\":{\"x\":368,\"y\":389},\"leftCheek7\":{\"x\":385,\"y\":397},\"chin\":{\"x\":403,\"y\":396},\"rightCheek7\":{\"x\":421,\"y\":385},\"rightCheek6\":{\"x\":433,\"y\":368},\"rightCheek5\":{\"x\":440,\"y\":350},\"rightCheek4\":{\"x\":443,\"y\":329},\"rightCheek3\":{\"x\":441,\"y\":313},\"rightCheek2\":{\"x\":437,\"y\":294},\"rightCheek1\":{\"x\":430,\"y\":273},\"parietal\":{\"x\":346,\"y\":206},\"rightEyebrowOutsideEnd\":{\"x\":413,\"y\":265},\"rightEyebrowUpperOutside\":{\"x\":398,\"y\":257},\"rightEyebrowUpperInside\":{\"x\":381,\"y\":264},\"rightEyebrowInsideEnd\":{\"x\":372,\"y\":276},\"rightEyebrowLowerInside\":{\"x\":385,\"y\":271},\"rightEyebrowLowerOutside\":{\"x\":399,\"y\":265},\"leftEyebrowOutsideEnd\":{\"x\":318,\"y\":293},\"leftEyebrowUpperOutside\":{\"x\":326,\"y\":280},\"leftEyebrowUpperInside\":{\"x\":343,\"y\":278},\"leftEyebrowInsideEnd\":{\"x\":353,\"y\":283},\"leftEyebrowLowerInside\":{\"x\":342,\"y\":286},\"leftEyebrowLowerOutside\":{\"x\":329,\"y\":287},\"leftEyeOutsideEnd\":{\"x\":328,\"y\":303},\"leftEyeUpperOutside\":{\"x\":333,\"y\":298},\"leftEyeUpperCenter\":{\"x\":338,\"y\":295},\"leftEyeUpperInside\":{\"x\":344,\"y\":295},\"leftEyeInsideEnd\":{\"x\":352,\"y\":298},\"leftEyeLowerInside\":{\"x\":346,\"y\":301},\"leftEyeLowerCenter\":{\"x\":340,\"y\":303},\"leftEyeLowerOutside\":{\"x\":334,\"y\":304},\"rightEyeOutsideEnd\":{\"x\":408,\"y\":277},\"rightEyeUpperOutside\":{\"x\":400,\"y\":276},\"rightEyeUpperCenter\":{\"x\":393,\"y\":277},\"rightEyeUpperInside\":{\"x\":387,\"y\":281},\"rightEyeInsideEnd\":{\"x\":383,\"y\":288},\"rightEyeLowerInside\":{\"x\":389,\"y\":286},\"rightEyeLowerCenter\":{\"x\":396,\"y\":285},\"rightEyeLowerOutside\":{\"x\":402,\"y\":281},\"noseLeftLineUpper\":{\"x\":359,\"y\":295},\"noseLeftLineCenter\":{\"x\":361,\"y\":314},\"noseLeftLineLower0\":{\"x\":356,\"y\":330},\"noseLeftLineLower1\":{\"x\":363,\"y\":336},\"noseBottomCenter\":{\"x\":378,\"y\":336},\"noseRightLineLower1\":{\"x\":393,\"y\":326},\"noseRightLineLower0\":{\"x\":395,\"y\":318},\"noseRightLineCenter\":{\"x\":381,\"y\":307},\"noseRightLineUpper\":{\"x\":373,\"y\":291},\"nostrilsLeft\":{\"x\":367,\"y\":335},\"nostrilsRight\":{\"x\":387,\"y\":328},\"noseTip\":{\"x\":373,\"y\":326},\"noseCenterLine0\":{\"x\":371,\"y\":310},\"noseCenterLine1\":{\"x\":366,\"y\":293},\"mouthLeftEnd\":{\"x\":356,\"y\":349},\"upperLipUpperLeftOutside\":{\"x\":366,\"y\":346},\"upperLipUpperLeftInside\":{\"x\":374,\"y\":344},\"mouthUpperPart\":{\"x\":381,\"y\":343},\"upperLipUpperRightInside\":{\"x\":388,\"y\":339},\"upperLipUpperRightOutside\":{\"x\":398,\"y\":336},\"mouthRightEnd\":{\"x\":410,\"y\":333},\"lowerLipLowerRightOutside\":{\"x\":407,\"y\":346},\"lowerLipLowerRightInside\":{\"x\":399,\"y\":355},\"mouthLowerPart\":{\"x\":388,\"y\":361},\"lowerLipLowerLeftInside\":{\"x\":377,\"y\":362},\"lowerLipLowerLeftOutside\":{\"x\":367,\"y\":359},\"upperLipLowerLeft\":{\"x\":372,\"y\":348},\"upperLipLowerCenter\":{\"x\":382,\"y\":346},\"upperLipLowerRight\":{\"x\":394,\"y\":341},\"lowerLipUpperRight\":{\"x\":398,\"y\":347},\"lowerLipUpperCenter\":{\"x\":385,\"y\":354},\"lowerLipUpperLeft\":{\"x\":373,\"y\":355},\"mouthCenter\":{\"x\":384,\"y\":350},\"detectionPointTotal\":83},\"blinkJudge\":{\"blinkLevel\":{\"leftEye\":100,\"rightEye\":103}},\"ageJudge\":{\"ageResult\":18,\"ageScore\":642},\"genderJudge\":{\"genderResult\":1,\"genderScore\":1000},\"faceAngleJudge\":{\"pitch\":-13,\"yaw\":8,\"roll\":-18,\"faceAngleVector\":{\"x\":-3,\"y\":12},\"gazeVector\":{\"x\":-3,\"y\":-11}},\"smileJudge\":{\"smileLevel\":59},\"registrationFaceInfo\":{\"meta\":{},\"faceId\":3,\"imagePath\":\"http://sample.hogehoge.example.jp/webapi/public/90a9cd77d26db5331d32a672baa66d94/20150726223647181_PC.jpg\"}}],\"errorInfo\":\"\",\"errorMessage\":\"\"}}}";
//        String jsonString = "{\"meta\":{},\"faceId\":+41,\"imagePath\":\"http://sample.hogehoge.example.jp/webapi/public/90a9cd77d26db5331d32a672baa66d94/20150726223647181_PC.jpg\"}";
//        String jsonString = "{\"array\":[{\"key\":\"value\"},\"tmp\",\"tmp2\",null],\"empty\":-0.25}";

//        JSONObject json = new JSONObject(jsonString);


        String jsonString = "[{\n" +
                "  \"name\" : { \"first\" : \"太郎\", \"last\" : \"技評\" }, \n" +
                "  \"mail\" : \"taro@example.jp\", \n" +
                "  \"todo\" : { \"work\" : \"hogehoge\", \"limit\" : \"2012/02/13\" }\n" +
                "}, \n" +
                "{\n" +
                "  \"name\" : { \"first\" : \"次郎\", \"last\" : \"技術\" }, \n" +
                "  \"mail\" : \"jiro@example.jp\", \n" +
                "  \"todo\" : { \"work\" : \"hogehoge\", \"limit\" : \"2012/02/15\" }\n" +
                "}, \n" +
                "{\n" +
                "  \"name\" : { \"first\" : \"花子\", \"last\" : \"評論\" }, \n" +
                "  \"mail\" : \"hanako@example.jp\", \n" +
                "  \"todo\" : { \"work\" : \"hogepiyo\", \"limit\" : \"2012/02/28\" }\n" +
                "}]";
        JSONArray json = new JSONArray(jsonString);

        LinkedList<JSONToken> list = new LinkedList<>();
        JSONTokenListBuilder.build(json, list);


        Log.d(TAG, json.toString());
        Log.d(TAG, "");
        for (JSONToken token: list) {
            Log.d(TAG, token.getValue().to_s() +
                    "\t\t" + token.getType().isSymbol() +
                    "\t\t" + token.getType().toString() +
                    "\t\t" + token.getIndexNumber()
            );
        }
        Log.d(TAG, "");

    }

    public void debugRingBuffer() {
        final int LOOP_SIZE = 14;
        RingBuffer<Integer> buf = new RingBuffer<>(6);
        buf.setCallback(leakedList -> Log.d(TAG, "leaked list is " + leakedList.toString()));

        Log.d(TAG, "buf size is      " + buf.size());
        Log.d(TAG, "InsertedSize is  " + buf.insertedLength());
        Log.d(TAG, "filled status is " + buf.isFill());
        Log.d(TAG, "header point is  "+buf.getIndex());
        Log.d(TAG, "------- FIFO ------ LIFO -------");
        for(int i=0; i < LOOP_SIZE; i++) {
            Log.d(TAG, i+"\t\tbuf["+i%buf.size()+"] = "+buf.get(i, RingBuffer.MODE.FIFO)
                    +"\tbuf["+i%buf.size()+"] = "+buf.get(i, RingBuffer.MODE.LIFO));
        }
        Log.d(TAG, "");

        buf.set(1, 2, 3);
        buf.swap(1, 2);
        Log.d(TAG, "Insert ->        1, 2, 3");
        Log.d(TAG, "buf size is      " + buf.size());
        Log.d(TAG, "InsertedSize is  " + buf.insertedLength());
        Log.d(TAG, "filled status is " + buf.isFill());
        Log.d(TAG, "header point is  " + buf.getIndex());
        Log.d(TAG, "------- FIFO ------ LIFO -------");
        for(int i=0; i < LOOP_SIZE; i++) {
            Log.d(TAG, i+"\t\tbuf["+i%buf.size()+"] = "+buf.get(i, RingBuffer.MODE.FIFO)
                    +"\tbuf["+i%buf.size()+"] = "+buf.get(i, RingBuffer.MODE.LIFO));
        }
        Log.d(TAG, "");

        buf.push(4);
        buf.set(5, 6, 7);
        Log.d(TAG, "Insert ->        4, 5, 6, 7");
        Log.d(TAG, "buf size is      " + buf.size());
        Log.d(TAG, "InsertedSize is  "+buf.insertedLength());
        Log.d(TAG, "filled status is " + buf.isFill());
        Log.d(TAG, "header point is  " + buf.getIndex());
        Log.d(TAG, "------- FIFO ------ LIFO -------");
        for(int i=0; i < LOOP_SIZE; i++) {
            Log.d(TAG, i+"\t\tbuf["+i%buf.size()+"] = "+buf.get(i, RingBuffer.MODE.FIFO)
                    +"\tbuf["+i%buf.size()+"] = "+buf.get(i, RingBuffer.MODE.LIFO));
        }
        Log.d(TAG, "");

        Log.d(TAG, "delete ->        "+buf.remove()+", "+buf.remove()+", "+buf.remove()+", "+buf.remove());
        Log.d(TAG, "buf size is      " + buf.size());
        Log.d(TAG, "InsertedSize is  "+buf.insertedLength());
        Log.d(TAG, "filled status is " + buf.isFill());
        Log.d(TAG, "header point is  "+buf.getIndex());
        Log.d(TAG, "------- FIFO ------ LIFO -------");
        for(int i=0; i < LOOP_SIZE; i++) {
            Log.d(TAG, i+"\t\tbuf["+i%buf.size()+"] = "+buf.get(i, RingBuffer.MODE.FIFO)
                    +"\tbuf["+i%buf.size()+"] = "+buf.get(i, RingBuffer.MODE.LIFO));
        }
        Log.d(TAG, "");

        buf.set(10, 20, 30);
        Log.d(TAG, "Insert ->        10, 20, 30");
        Log.d(TAG, "buf size is      " + buf.size());
        Log.d(TAG, "InsertedSize is  "+buf.insertedLength());
        Log.d(TAG, "filled status is "+buf.isFill());
        Log.d(TAG, "header point is  " + buf.getIndex());
        Log.d(TAG, "------- FIFO ------ LIFO -------");
        for(int i=0; i < LOOP_SIZE; i++) {
            Log.d(TAG, i+"\t\tbuf["+i%buf.size()+"] = "+buf.get(i, RingBuffer.MODE.FIFO)
                    +"\tbuf["+i%buf.size()+"] = "+buf.get(i, RingBuffer.MODE.LIFO));
        }
        Log.d(TAG, "");

        Log.d(TAG, "delete ->        "+buf.remove(2).toString());
        Log.d(TAG, "buf size is      " + buf.size());
        Log.d(TAG, "InsertedSize is  "+buf.insertedLength());
        Log.d(TAG, "filled status is "+ buf.isFill());
        Log.d(TAG, "header point is  "+buf.getIndex());
        Log.d(TAG, "------- FIFO ------ LIFO -------");
        for(int i=0; i < LOOP_SIZE; i++) {
            Log.d(TAG, i+"\t\tbuf["+i%buf.size()+"] = "+buf.get(i, RingBuffer.MODE.FIFO)
                    +"\tbuf["+i%buf.size()+"] = "+buf.get(i, RingBuffer.MODE.LIFO));
        }
        Log.d(TAG, "");

        buf.moveHeader(1);
        Log.d(TAG, "moved head ->    1");
        Log.d(TAG, "buf size is      " + buf.size());
        Log.d(TAG, "InsertedSize is  "+buf.insertedLength());
        Log.d(TAG, "filled status is "+ buf.isFill());
        Log.d(TAG, "header point is  " + buf.getIndex());
        Log.d(TAG, "------- FIFO ------ LIFO -------");
        for(int i=0; i < LOOP_SIZE; i++) {
            Log.d(TAG, i+"\t\tbuf["+i%buf.size()+"] = "+buf.get(i, RingBuffer.MODE.FIFO)
                    +"\tbuf["+i%buf.size()+"] = "+buf.get(i, RingBuffer.MODE.LIFO));
        }
        Log.d(TAG, "");

        buf.set(11, 21, 31, 41, 51, 61, 71);
        Log.d(TAG, "Insert ->        11, 21, 31, 41, 51, 61, 71");
        Log.d(TAG, "buf size is      " + buf.size());
        Log.d(TAG, "InsertedSize is  "+buf.insertedLength());
        Log.d(TAG, "filled status is "+buf.isFill());
        Log.d(TAG, "header point is  " + buf.getIndex());
        Log.d(TAG, "------- FIFO ------ LIFO -------");
        for(int i=0; i < LOOP_SIZE; i++) {
            Log.d(TAG, i+"\t\tbuf["+i%buf.size()+"] = "+buf.get(i, RingBuffer.MODE.FIFO)
                    +"\tbuf["+i%buf.size()+"] = "+buf.get(i, RingBuffer.MODE.LIFO));
        }
        Log.d(TAG, "");

        buf.resize(4);
        Log.d(TAG, "buf size is      "+buf.size());
        Log.d(TAG, "InsertedSize is  " + buf.insertedLength());
        Log.d(TAG, "filled status is "+buf.isFill());
        Log.d(TAG, "header point is  "+buf.getIndex());
        Log.d(TAG, "------- FIFO ------ LIFO -------");
        for(int i=0; i < LOOP_SIZE; i++) {
            Log.d(TAG, i+"\t\tbuf["+i%buf.size()+"] = "+buf.get(i, RingBuffer.MODE.FIFO)
                    +"\tbuf["+i%buf.size()+"] = "+buf.get(i, RingBuffer.MODE.LIFO));
        }
        Log.d(TAG, "");

        Log.d(TAG, "pop ->           " + buf.pop(1).toString());
        Log.d(TAG, "buf size is      "+buf.size());
        Log.d(TAG, "InsertedSize is  "+buf.insertedLength());
        Log.d(TAG, "filled status is "+ buf.isFill());
        Log.d(TAG, "header point is  "+buf.getIndex());
        Log.d(TAG, "------- FIFO ------ LIFO -------");
        for(int i=0; i < LOOP_SIZE; i++) {
            Log.d(TAG, i+"\t\tbuf["+i%buf.size()+"] = "+buf.get(i, RingBuffer.MODE.FIFO)
                    +"\tbuf["+i%buf.size()+"] = "+buf.get(i, RingBuffer.MODE.LIFO));
        }
        Log.d(TAG, "");

//        Log.d(TAG, "pop ->           " + buf.pop(5).toString());
//        Log.d(TAG, "buf size is      "+buf.size());
//        Log.d(TAG, "InsertedSize is  "+buf.insertedLength());
//        Log.d(TAG, "filled status is "+ buf.isFill());
//        Log.d(TAG, "header point is  "+buf.getIndex());
//        Log.d(TAG, "------- FIFO ------ LIFO -------");
//        for(int i=0; i < LOOP_SIZE; i++) {
//            Log.d(TAG, i+"\t\tbuf["+i%buf.size()+"] = "+buf.get(i, RingBuffer.MODE.FIFO)
//                    +"\tbuf["+i%buf.size()+"] = "+buf.get(i, RingBuffer.MODE.LIFO));
//        }
//        Log.d(TAG, "");

        RingBuffer<Integer> buf2 = buf.clone();
        buf2.push(31, 32, 33);
        buf2.resize(3);
        Log.d(TAG, "Insert ->        31, 32, 33");
        Log.d(TAG, "buf2 size is     " + buf2.size());
        Log.d(TAG, "InsertedSize is  "+buf2.insertedLength());
        Log.d(TAG, "filled status is "+ buf2.isFill());
        Log.d(TAG, "header point is  "+buf2.getIndex());
        Log.d(TAG, "------- FIFO ------ LIFO -------");
        for(int i=0; i < LOOP_SIZE; i++) {
            Log.d(TAG, i+"\t\tbuf["+i%buf2.size()+"] = "+buf2.get(i, RingBuffer.MODE.FIFO)
                    +"\tbuf["+i% buf2.size()+"] = "+buf2.get(i, RingBuffer.MODE.LIFO));
        }
        Log.d(TAG, "");

        Log.d(TAG, "buf size is      " + buf.size());
        Log.d(TAG, "InsertedSize is  "+buf.insertedLength());
        Log.d(TAG, "filled status is "+ buf.isFill());
        Log.d(TAG, "header point is  "+buf.getIndex());
        Log.d(TAG, "------- FIFO ------ LIFO -------");
        for(int i=0; i < LOOP_SIZE; i++) {
            Log.d(TAG, i+"\t\tbuf["+i%buf.size()+"] = "+buf.get(i, RingBuffer.MODE.FIFO)
                    +"\tbuf["+i%buf.size()+"] = "+buf.get(i, RingBuffer.MODE.LIFO));
        }
        Log.d(TAG, "");

        RingBuffer<Integer> buf3 = buf;
        buf3.moveHeader(2);
        buf3.push(1, 2, 3);
        Log.d(TAG, "Insert ->        1, 2, 3");
        Log.d(TAG, "buf3 size is     " + buf3.size());
        Log.d(TAG, "InsertedSize is  "+buf3.insertedLength());
        Log.d(TAG, "filled status is "+ buf3.isFill());
        Log.d(TAG, "header point is  " + buf3.getIndex());
        Log.d(TAG, "------- FIFO ------ LIFO -------");
        for(int i=0; i < LOOP_SIZE; i++) {
            Log.d(TAG, i+"\t\tbuf["+i%buf3.size()+"] = "+buf3.get(i, RingBuffer.MODE.FIFO)
                    +"\tbuf["+i%buf3.size()+"] = "+buf3.get(i, RingBuffer.MODE.LIFO));
        }
        Log.d(TAG, "");

        Log.d(TAG, "buf size is      "+buf.size());
        Log.d(TAG, "InsertedSize is  "+buf.insertedLength());
        Log.d(TAG, "filled status is "+ buf.isFill());
        Log.d(TAG, "header point is  "+buf.getIndex());
        Log.d(TAG, "------- FIFO ------ LIFO -------");
        for(int i=0; i < LOOP_SIZE; i++) {
            Log.d(TAG, i+"\t\tbuf["+i%buf.size()+"] = "+buf.get(i, RingBuffer.MODE.FIFO)
                    +"\tbuf["+i%buf.size()+"] = "+buf.get(i, RingBuffer.MODE.LIFO));
        }
        Log.d(TAG, "");

        buf.reverse();
        buf.swap(1, 4);
        Log.d(TAG, "buf size is      "+buf.size());
        Log.d(TAG, "InsertedSize is  "+buf.insertedLength());
        Log.d(TAG, "filled status is "+ buf.isFill());
        Log.d(TAG, "header point is  "+buf.getIndex());
        Log.d(TAG, "------- FIFO ------ LIFO -------");
        for(int i=0; i < LOOP_SIZE; i++) {
            Log.d(TAG, i+"\t\tbuf["+i%buf.size()+"] = "+buf.get(i, RingBuffer.MODE.FIFO)
                    +"\tbuf["+i%buf.size()+"] = "+buf.get(i, RingBuffer.MODE.LIFO));
        }
        Log.d(TAG, "");

        if(buf.equals(buf2)) {
            Log.d(TAG, "buf == buf2");
        }
        if(buf2.equals(buf3)) {
            Log.d(TAG, "buf2 == buf3");
        }
        if(buf3.equals(buf)) {
            Log.d(TAG, "buf3 == buf");
        }
        Log.d(TAG, "");

        Log.d(TAG, ""+buf.toString());

    }

}

class Log {
    public static void d(String TAG, String msg) {
        System.out.println(TAG + "\t" + msg);
    }
}
