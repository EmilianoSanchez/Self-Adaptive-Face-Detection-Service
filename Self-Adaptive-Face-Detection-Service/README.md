# Self-Adaptive-Face-Detection-Service
An Android service that provides enhanced Face Detection through the runtime selection and composition of third-party face detection services.

## Abstract:
Face detection is a classic problem in Computer Vision. It consists on identifying human faces in digital images and videos. With the proliferation of mobile and camera-equipped devices, face detection technologies are being used in a variety of applications including games, biometry, augmented reality, photography and marketing. Face detection algorithms were usually distributed in the form of software libraries like OpenCV, but nowadays many solutions are provided as Web services. While the former perform faster and can be used in offline scenarios, the latest are known to be more accurate and generally provide additional features like age, gender or smile classification. However, performance and accuracy of face detection services may vary considerable according to image characteristics, like its size, format, etc, and execution context characteristics, like CPU usage and network connection type. 

The proposed Android service wraps and consumes third-party face detection services through a single interface to use for developing Android apps. The service switches these alternatives dynamically considering image characteristics, context changes and user preferences regarding functional and non-functional requirements. The self-adaptation is done with an approach that represents system variability with feature models, and selects service variants using quality criteria. These criteria are based on prediction models that estimate response time and accuracy of individual services.

## Included third-party services:
- Android services:
-- OpenCV Manager
-- Google Play Services Face API

- Web services:
-- Face Rect
-- Sky Biometry
-- Project Oxford