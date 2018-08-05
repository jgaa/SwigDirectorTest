//
// Created by jgaa on 04.08.18.
//

#ifndef TESTSWIG_NATIVERESTAPI_H
#define TESTSWIG_NATIVERESTAPI_H

#include <string>
#include <functional>

namespace restapi {

    class NativeRestApi {
    public:

        using completion_t = std::function<void (int httpCode, const std::string& body)>;

        static int getAnswer();

        std::string quoteMe(const std::string& quote);

        void sendPostRequest(const std::string& url,
                             const std::string jsonObject,
                             completion_t completion);

    };

} // namespace

#endif //TESTSWIG_NATIVERESTAPI_H
