# Base image
FROM gcr.io/google.com/cloudsdktool/google-cloud-cli:stable

# Install MySQL client
RUN apt-get update && apt-get install -y default-mysql-client && rm -rf /var/lib/apt/lists/*

# Copy init script
COPY fetch_secrets.sh /fetch_secrets.sh
RUN chmod +x /fetch_secrets.sh

# Entrypoint
ENTRYPOINT ["/fetch_secrets.sh"]
