# GenAI Docker Review

- I accepted pinning the Maven and JRE base images by digest. This makes the build reproducible instead of silently changing when a tag is updated.
- I rejected running as a non-root user for this exercise because it requires an explicit filesystem ownership check and changes where the runtime may write; it should be handled deliberately in a later hardening pass.
- The startup-race answer matched the experiment: `depends_on` alone only orders container creation. A MySQL healthcheck plus `condition: service_healthy` is what waits for readiness; sleep and restart loops only hide the race.

