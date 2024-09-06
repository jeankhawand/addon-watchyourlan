ARG BUILD_FROM
FROM $BUILD_FROM
# Setup base
ARG BUILD_ARCH=aarch64
ARG VERSION="2.0.1"
ARG BUILD_DATE
ARG BUILD_DESCRIPTION
ARG BUILD_NAME
ARG BUILD_REF
ARG BUILD_REPOSITORY
ARG BUILD_VERSION
# Set shell
SHELL ["/bin/bash", "-o", "pipefail", "-c"]

# Set S6 verbosity level
ENV S6_VERBOSITY=1

# Download and install the APK based on architecture
RUN echo "http://dl-cdn.alpinelinux.org/alpine/edge/community" >> /etc/apk/repositories && \
    apk update && \
    apk add --no-cache arp-scan wget

# Determine architecture and download/install WatchYourLAN
RUN case "$(uname -m)" in \
        "aarch64") arch="arm64" ;; \
        "i386") arch="386" ;; \
        *) echo "Unsupported architecture"; exit 1 ;; \
    esac && \
    wget -O /usr/watchyourlan.tar.gz "https://github.com/aceberg/WatchYourLAN/releases/download/${VERSION}/watchyourlan_${VERSION}_linux_${arch}.tar.gz" && \
    tar -xzf /usr/watchyourlan.tar.gz -C /usr/bin --strip-components=1 "watchyourlan_${VERSION}_linux_${arch}/watchyourlan" && \
    chmod +x /usr/bin/watchyourlan && \
    rm -f /usr/watchyourlan.tar.gz

# Labels
LABEL \
    io.hass.name="${BUILD_NAME}" \
    io.hass.description="${BUILD_DESCRIPTION}" \
    io.hass.arch="${BUILD_ARCH}" \
    io.hass.type="addon" \
    io.hass.version=${BUILD_VERSION} \
    maintainer="Jena Khawand <https://github.com/jeankhawand/>" \
    org.opencontainers.image.title="${BUILD_NAME}" \
    org.opencontainers.image.description="${BUILD_DESCRIPTION}" \
    org.opencontainers.image.vendor="Jena Khawand <https://github.com/jeankhawand/>" \
    org.opencontainers.image.authors="Jena Khawand <https://github.com/jeankhawand/>" \
    org.opencontainers.image.licenses="MIT" \
    org.opencontainers.image.url="https://github.com/${BUILD_REPOSITORY}" \
    org.opencontainers.image.source="https://github.com/${BUILD_REPOSITORY}" \
    org.opencontainers.image.documentation="https://github.com/${BUILD_REPOSITORY}/blob/main/README.md" \
    org.opencontainers.image.created=${BUILD_DATE} \
    org.opencontainers.image.revision=${BUILD_REF} \
    org.opencontainers.image.version=${BUILD_VERSION}
# Copy root filesystem
COPY rootfs /

HEALTHCHECK \
    --interval=5s \
    --retries=5 \
    --start-period=30s \
    --timeout=25s \
    CMD curl --fail "http://localhost:8840" &>/dev/null || exit 1