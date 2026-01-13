# Boost setup for quantlib_for_maven
#
# This module is responsible for finding Boost in a way that works across
# CMake versions:
#   - CMake >= 3.30 prefers Boost's upstream BoostConfig.cmake (CONFIG mode)
#   - With CMP0167 set to OLD, the CONFIG keyword is rejected -> fall back to
#     module mode (FindBoost) where available.
#
# Expected inputs:
#   - QL_MVN_BOOST_VERSION (string): Boost version to require.

if (NOT DEFINED QL_MVN_BOOST_VERSION)
    message(FATAL_ERROR "QL_MVN_BOOST_VERSION must be set before including Boost.cmake")
endif()

# Keep behavior stable: silence warnings about newer Boost versions.
set(Boost_NO_WARN_NEW_VERSIONS ON)

# The FindBoost module is removed in CMake 3.30; prefer upstream BoostConfig.cmake.
# When CMP0167 is OLD, the CONFIG keyword is rejected, so fall back to module mode.
if (POLICY CMP0167)
    cmake_policy(GET CMP0167 _ql_mvn_cmp0167)
endif()

if (NOT DEFINED _ql_mvn_cmp0167 OR _ql_mvn_cmp0167 STREQUAL "NEW")
    find_package(Boost ${QL_MVN_BOOST_VERSION} EXACT CONFIG REQUIRED)
else()
    find_package(Boost ${QL_MVN_BOOST_VERSION} EXACT REQUIRED)
endif()

unset(_ql_mvn_cmp0167)

