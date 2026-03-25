#include "process_inspector.h"

namespace phonescope {

std::string ProcessInspector::inspect() {
    // TODO: Sprint S6 — Process monitor
    // - Enumerate /proc/[pid]/ directories
    // - For each: read status, stat, oom_score, cgroup
    // - Calculate per-PID CPU usage (delta over 1s)
    // - Read VmRSS, VmSwap, thread count, FD count
    // - Sort by CPU or RAM usage
    return "{}";
}

} // namespace phonescope
