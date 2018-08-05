//
// Created by jgaa on 04.08.18.
//

#include <chrono>
#include <thread>

#include "NativeRestApi.h"

namespace restapi {

    int NativeRestApi::getAnswer() {
        return 42;
    }

    std::string NativeRestApi::quoteMe(const std::string &quote) {
        return std::string("'") + quote + "'";
    }

    void NativeRestApi::sendPostRequest(const std::string &url,
                                        const std::string jsonObject,
                                        NativeRestApi::completion_t completion) {

        std::thread{([completion{move(completion)}] {
            std::this_thread::sleep_for(std::chrono::seconds(3));
            if (completion) {
                completion(200, R"({"object" : "something", "success" : true})");
            }

        })}.detach();
    }

} // namespace
